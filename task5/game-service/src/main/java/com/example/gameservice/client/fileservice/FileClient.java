package com.example.gameservice.client.fileservice;

import com.example.gameservice.client.fileservice.dto.FileResponseDto;
import com.example.gameservice.client.fileservice.dto.FileWithDataResponseDto;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class FileClient {

    private static final String FILE_SERVICE_GATEWAY_ENDPOINT = "/file-service/api";
    private final String fileServiceUrl;
    private final String fileServiceGetFileByIdEndpoint;
    private final String fileServiceUploadFileEndpoint;
    private final String fileServiceDeleteFileByIdEndpoint;
//    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;


    public FileClient(
            EurekaClient eurekaClient,
            @Value("${external-services.file-service.endpoints.get-file-by-id}") String fileServiceGetFileByIdEndpoint,
            @Value("${external-services.file-service.endpoints.upload-file}") String fileServiceUploadFileEndpoint,
            @Value("${external-services.file-service.endpoints.delete-file-by-id}") String fileServiceDeleteFileEndpoint,
            ReactorLoadBalancerExchangeFilterFunction lbFunction
    ) {
        String gatewayUrl = eurekaClient.getApplication("gateway").getInstances().get(0).getHomePageUrl();
        this.fileServiceUrl = gatewayUrl + FILE_SERVICE_GATEWAY_ENDPOINT;
        this.fileServiceGetFileByIdEndpoint = fileServiceGetFileByIdEndpoint;
        this.fileServiceUploadFileEndpoint = fileServiceUploadFileEndpoint;
        this.fileServiceDeleteFileByIdEndpoint = fileServiceDeleteFileEndpoint;
//        this.lbFunction = lbFunction;
    }


    public Mono<FileWithDataResponseDto> getById(UUID fileId) {
        return WebClient.builder()
                .baseUrl(fileServiceUrl)
//                .filter(lbFunction)
                .build()
                .get()
                .uri(fileServiceGetFileByIdEndpoint, Map.of("id", fileId))
                .retrieve()
                .bodyToMono(FileWithDataResponseDto.class);
    }

    public Mono<FileResponseDto> upload(FilePart filePart) {
        return WebClient.create()
                .post()
                .uri(fileServiceUrl + fileServiceUploadFileEndpoint)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", filePart))
                .retrieve()
                .bodyToMono(FileResponseDto.class);
    }

    public Mono<Void> deleteById(UUID fileId) {
        return WebClient.create()
                .delete()
                .uri(fileServiceUrl + fileServiceDeleteFileByIdEndpoint, Map.of("id", fileId))
                .retrieve()
                .bodyToMono(Void.class);
    }

}
