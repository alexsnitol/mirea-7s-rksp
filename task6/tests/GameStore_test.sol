// SPDX-License-Identifier: GPL-3.0
        
pragma solidity >=0.4.22 <0.9.0;

import "remix_tests.sol"; 

import "remix_accounts.sol";
import "../contracts/GameStore.sol";

contract testSuite {
    Shop testShop;

    function beforeAll() public {
        testShop = new Shop();
    }

    function checkGameAfterAdding() public {
        string memory uuid = "7eea2c11-8b62-47ce-a093-46636ea6f527";
        string memory title = "Game title";
        uint priceWei = 1;

        testShop.saveOrUpdateGame(
            uuid,
            title,
            priceWei
        );

        Assert.equal(title, testShop.getGameTitleByUuid(uuid), "Title is not correct");
        Assert.equal(priceWei, testShop.getGamePriceByUuid(uuid), "Price in wei is not correct");
        Assert.equal(0, testShop.getGamePaymentsNumberByUuid(uuid), "Initial payments number should be 0");
    }

    function checkGameAfterAddingAndUpdate() public {
        string memory uuid = "7eea2c11-8b62-47ce-a093-46636ea6f527";
        string memory title = "Game title";
        uint priceWei = 1;

        testShop.saveOrUpdateGame(
            uuid,
            title,
            priceWei
        );

        Assert.equal(title, testShop.getGameTitleByUuid(uuid), "Title is not correct");
        Assert.equal(priceWei, testShop.getGamePriceByUuid(uuid), "Price in wei is not correct");
        Assert.equal(0, testShop.getGamePaymentsNumberByUuid(uuid), "Initial payments number should be 0");

        priceWei = 10;

        testShop.saveOrUpdateGame(
            uuid,
            title,
            priceWei
        );

        Assert.equal(title, testShop.getGameTitleByUuid(uuid), "Title is not correct");
        Assert.equal(priceWei, testShop.getGamePriceByUuid(uuid), "Price in wei is not correct");
        Assert.equal(0, testShop.getGamePaymentsNumberByUuid(uuid), "Initial payments number should be 0");
    }
}
    