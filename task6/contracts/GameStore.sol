// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Shop {
    address public owner;
    mapping (address => string[]) public accountGames;
    
    struct Game {
        string uuid;
        string title;
        uint priceWei;
        uint paymentsNumber;
        mapping (address => uint[]) payments;
    }

    mapping (string => Game) public games;


    constructor() {
        owner = msg.sender;
    }


    function payGame(string memory uuidGame) public payable {
        Game storage game = games[uuidGame];
        
        require(msg.value == game.priceWei);
        game.paymentsNumber = ++game.paymentsNumber;
        game.payments[msg.sender].push(msg.value);
        accountGames[msg.sender].push(uuidGame);
    }

    function saveOrUpdateGame(string memory uuid, string memory title, uint priceWei) public {
        require(msg.sender == owner);

        Game storage game = games[uuid];
        game.uuid = uuid;
        game.title = title;
        game.priceWei = priceWei;
    }

    function withdrawAll() public {
        require(msg.sender == owner);

        address payable paybleOwner = payable(owner);
        address thisContract = address(this);
        paybleOwner.transfer(thisContract.balance);
    }

    function getGameTitleByUuid(string memory uuid) public view returns (string memory) {
        return games[uuid].title;
    }

    function getGamePriceByUuid(string memory uuid) public view returns (uint) {
        return games[uuid].priceWei;
    }

    function getGamePaymentsNumberByUuid(string memory uuid) public view returns (uint) {
        return games[uuid].paymentsNumber;
    }

    function getGamesOfAccountByAdders(address account) public view returns (string[] memory) {
        return accountGames[account];
    }

}