
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `decliningxchange`
--

-- --------------------------------------------------------

--
-- Table structure for table `are`
--

CREATE TABLE `are` (
  `StudentID` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `biddingOn`
--

CREATE TABLE `biddingOn` (
  `StudentID` int(8) NOT NULL,
  `ItemID` int(15) NOT NULL,
  `Bid` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `completedTransactions`
--

CREATE TABLE `completedTransactions` (
  `ItemID` int(15) NOT NULL,
  `SellerID` int(8) NOT NULL,
  `BuyerID` int(8) NOT NULL,
  `PurchasePrice` int(20) NOT NULL,
  `cdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `completedTransactions`
--
DELIMITER $$
CREATE TRIGGER `deletedTransactionsLogs` BEFORE DELETE ON `completedTransactions` FOR EACH ROW INSERT INTO completedTransactionsLogs VALUES(OLD.ItemID, OLD.SellerID,OLD.BuyerID,OLD.PurchasePrice,"Deleted",NOW())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insertedTransactionLogs` AFTER INSERT ON `completedTransactions` FOR EACH ROW INSERT INTO completedTransactionsLogs VALUES(NEW.ItemID, NEW.SellerID, NEW.BuyerID,NEW.PurchasePrice,"Inserted",NOW())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updatedTransactionLogs` AFTER UPDATE ON `completedTransactions` FOR EACH ROW INSERT INTO completedTransactionsLogs VALUES(`NEW.ItemID`, `NEW.SellerID`,`NEW.BuyerID`, `NEW.PurchasePrice`, "Updated", NOW())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `completedTransactionsLogs`
--

CREATE TABLE `completedTransactionsLogs` (
  `ItemID` int(15) NOT NULL,
  `SellerID` int(8) NOT NULL,
  `BuyerID` int(8) NOT NULL,
  `PurchasePrice` int(20) NOT NULL,
  `action` varchar(20) NOT NULL,
  `cdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `has`
--

CREATE TABLE `has` (
  `ItemID` int(15) NOT NULL,
  `SellerID` int(8) NOT NULL,
  `BuyerID` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `listings`
--

CREATE TABLE `listings` (
  `StudentID` int(8) NOT NULL,
  `ItemID` int(15) NOT NULL,
  `RequestedDeclining` int(20) NOT NULL,
  `cdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `listings`
--
DELIMITER $$
CREATE TRIGGER `deleteListingsLog` BEFORE DELETE ON `listings` FOR EACH ROW INSERT INTO listingslogs VALUES(OLD.StudentID, OLD.ItemID, OLD.RequestedDeclining, "Deleted", NOW())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insertlistingLog` AFTER INSERT ON `listings` FOR EACH ROW INSERT INTO listingslogs VALUES(NEW.StudentID, NEW.ItemID, NEW.RequestedDeclining, "inserted", NOW())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateListingsLog` AFTER UPDATE ON `listings` FOR EACH ROW INSERT INTO listingslogs VALUES(NEW.StudentID, NEW.ItemID, NEW.RequestedDeclining, "Updated", NOW())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `listingslogs`
--

CREATE TABLE `listingslogs` (
  `StudentID` int(8) NOT NULL,
  `ItemID` int(15) NOT NULL,
  `RequestedDeclining` int(20) NOT NULL,
  `action` varchar(20) NOT NULL,
  `cdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ItemName` varchar(100) NOT NULL,
  `ItemID` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `StudentID` int(8) NOT NULL,
  `StudentName` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `DecliningBalance` int(20) NOT NULL,
  `Likes` int(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `are`
--
ALTER TABLE `are`
  ADD KEY `StudentID` (`StudentID`);

--
-- Indexes for table `biddingOn`
--
ALTER TABLE `biddingOn`
  ADD KEY `StudentID` (`StudentID`),
  ADD KEY `ItemID` (`ItemID`);

--
-- Indexes for table `completedTransactions`
--
ALTER TABLE `completedTransactions`
  ADD KEY `ItemID` (`ItemID`),
  ADD KEY `BuyerID` (`BuyerID`) USING BTREE,
  ADD KEY `SellerID` (`SellerID`) USING BTREE;

--
-- Indexes for table `has`
--
ALTER TABLE `has`
  ADD KEY `ItemID` (`ItemID`) USING BTREE,
  ADD KEY `BuyerID` (`BuyerID`) USING BTREE,
  ADD KEY `SellerID` (`SellerID`) USING BTREE;

--
-- Indexes for table `listings`
--
ALTER TABLE `listings`
  ADD KEY `StudentID` (`StudentID`) USING BTREE,
  ADD KEY `ItemID` (`ItemID`) USING BTREE;

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ItemID`),
  ADD UNIQUE KEY `ItemID` (`ItemID`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`StudentID`),
  ADD UNIQUE KEY `StudentID` (`StudentID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `are`
--
ALTER TABLE `are`
  ADD CONSTRAINT `areStudentIdConstraint` FOREIGN KEY (`StudentID`) REFERENCES `user` (`StudentID`);

--
-- Constraints for table `biddingOn`
--
ALTER TABLE `biddingOn`
  ADD CONSTRAINT `biddingOnConstraintItem` FOREIGN KEY (`ItemID`) REFERENCES `product` (`ItemID`),
  ADD CONSTRAINT `biddingOnConstraintStudent` FOREIGN KEY (`StudentID`) REFERENCES `user` (`StudentID`);

--
-- Constraints for table `completedTransactions`
--
ALTER TABLE `completedTransactions`
  ADD CONSTRAINT `compTransBuyer` FOREIGN KEY (`BuyerID`) REFERENCES `user` (`StudentID`),
  ADD CONSTRAINT `compTransItem` FOREIGN KEY (`ItemID`) REFERENCES `product` (`ItemID`),
  ADD CONSTRAINT `compTransSeller` FOREIGN KEY (`SellerID`) REFERENCES `user` (`StudentID`);

--
-- Constraints for table `has`
--
ALTER TABLE `has`
  ADD CONSTRAINT `hasBuyerId` FOREIGN KEY (`BuyerID`) REFERENCES `user` (`StudentID`),
  ADD CONSTRAINT `hasSellerId` FOREIGN KEY (`SellerID`) REFERENCES `user` (`StudentID`);

--
-- Constraints for table `listings`
--
ALTER TABLE `listings`
  ADD CONSTRAINT `itemIdListings` FOREIGN KEY (`ItemID`) REFERENCES `product` (`ItemID`),
  ADD CONSTRAINT `studentIdListings` FOREIGN KEY (`StudentID`) REFERENCES `user` (`StudentID`);

ALTER TABLE `listingslogs`
  ADD CONSTRAINT `itemIdListingss` FOREIGN KEY (`ItemID`) REFERENCES `product` (`ItemID`),
  ADD CONSTRAINT `studentIdListingss` FOREIGN KEY (`StudentID`) REFERENCES `user` (`StudentID`);

--
-- Constraints for table `completedTransactions`
--
ALTER TABLE `completedTransactionsLogs`
  ADD CONSTRAINT `compTransBuyerz` FOREIGN KEY (`BuyerID`) REFERENCES `user` (`StudentID`),
  ADD CONSTRAINT `compTransItemz` FOREIGN KEY (`ItemID`) REFERENCES `product` (`ItemID`),
  ADD CONSTRAINT `compTransSellerz` FOREIGN KEY (`SellerID`) REFERENCES `user` (`StudentID`);
