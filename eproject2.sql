-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 07, 2021 lúc 04:23 PM
-- Phiên bản máy phục vụ: 10.4.11-MariaDB
-- Phiên bản PHP: 7.2.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `eproject2`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `currentstocks`
--

CREATE TABLE `currentstocks` (
  `productID` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `currentstocks`
--

INSERT INTO `currentstocks` (`productID`, `quantity`) VALUES
('1', 186),
('2', 133),
('3', 120),
('4', 200);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customers`
--

CREATE TABLE `customers` (
  `customerID` int(11) NOT NULL,
  `customerCode` varchar(100) NOT NULL,
  `customerName` varchar(50) NOT NULL,
  `customerAddress` varchar(50) NOT NULL,
  `customerPhone` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `customers`
--

INSERT INTO `customers` (`customerID`, `customerCode`, `customerName`, `customerAddress`, `customerPhone`) VALUES
(1, 'cus1', 'thinh', 'hanoi', '123'),
(2, 'cus2', 'hieu', 'hanoi', '123');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `productID` int(11) NOT NULL,
  `productName` varchar(200) NOT NULL,
  `productCode` varchar(200) NOT NULL,
  `costprice` float NOT NULL,
  `sellingprice` float NOT NULL,
  `category` varchar(200) NOT NULL,
  `productImage` text DEFAULT NULL,
  `status` varchar(200) NOT NULL,
  `date` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`productID`, `productName`, `productCode`, `costprice`, `sellingprice`, `category`, `productImage`, `status`, `date`) VALUES
(1, 'Iphone', 'I12', 500, 1000, 'phone', 'D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\Images\\iphone-12-family-select-2020.jpg', 'AVAILABLE', '2021-03-07'),
(2, 'Samsung', 'S20', 450, 799, 'phone', 'D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\Images\\samsung-galaxy-s20-hong-600x600-600x600.jpg', 'AVAILABLE', '2021-03-07'),
(3, 'Macbook', 'MB2020', 700, 1799, 'laptop', 'D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\Images\\51zGqbn44EL._AC_SL1000_.jpg', 'AVAILABLE', '2021-03-07'),
(4, 'Huawei', 'H1', 300, 400, 'phone', 'D:\\Flynn\\java\\prj\\eproject2.github.io\\eProject2\\src\\Images\\huawei-p30-pro-1-600x600.jpg', 'AVAILABLE', '2021-03-07');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `recorddetail`
--

CREATE TABLE `recorddetail` (
  `productID` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `recordID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `recorddetail`
--

INSERT INTO `recorddetail` (`productID`, `quantity`, `recordID`) VALUES
(1, 100, 1),
(1, 50, 2),
(1, 50, 3),
(1, 2, 4),
(1, 20, 5),
(2, 20, 1),
(2, 100, 3),
(2, 5, 4),
(3, 100, 3),
(3, 10, 5),
(4, 50, 1),
(4, 100, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `records`
--

CREATE TABLE `records` (
  `recordID` int(11) NOT NULL,
  `recordCode` varchar(200) NOT NULL,
  `recordType` varchar(200) NOT NULL,
  `supplierID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `handleBy` int(11) NOT NULL,
  `date` varchar(200) NOT NULL,
  `totalPrice` double NOT NULL,
  `vat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `records`
--

INSERT INTO `records` (`recordID`, `recordCode`, `recordType`, `supplierID`, `customerID`, `handleBy`, `date`, `totalPrice`, `vat`) VALUES
(1, 'RC1', 'IMPORT', 2, 0, 2, '2021/03/07', 81400, 10),
(2, 'RC2', 'IMPORT', 4, 0, 2, '2021/03/07', 25000, 0),
(3, 'RC3', 'IMPORT', 1, 0, 2, '2021/03/08', 170000, 0),
(4, 'RC4', 'DELETED', 0, 2, 2, '2021/03/01', 5995, 0),
(5, 'RC5', 'EXPORT', 0, 1, 2, '2021/02/06', 37990, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `suppliers`
--

CREATE TABLE `suppliers` (
  `supplierID` int(11) NOT NULL,
  `supplierName` varchar(50) NOT NULL,
  `supplierCode` varchar(100) NOT NULL,
  `supplierContact` varchar(10) NOT NULL,
  `supplierLocation` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `suppliers`
--

INSERT INTO `suppliers` (`supplierID`, `supplierName`, `supplierCode`, `supplierContact`, `supplierLocation`) VALUES
(1, 'The gioi di dong', 'TGDD', '19001001', '54 Hai Ba Trung'),
(2, 'Dien May Xanh', 'DMX', '19001731', '36 Hoang Cau'),
(3, 'TIKI', 'TKI', '19001010', '69 Hoang Dao Thuy'),
(4, 'SHOPEE', 'SP', '19001788', 'Ho Chi Minh');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role` varchar(20) NOT NULL,
  `status` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`userID`, `fullname`, `location`, `phone`, `username`, `password`, `role`, `status`) VALUES
(1, 'le van dung', 'hanoi', '0123456789', 'ledung', '202cb962ac59075b964b07152d234b70', 'ADMINISTRATOR', 'AVAILABLE'),
(2, 'doan trung hieu', 'hanoi', '1234', 'hieu', '202cb962ac59075b964b07152d234b70', 'NORMAL USER', 'AVAILABLE');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerID`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`productID`),
  ADD UNIQUE KEY `productCode` (`productCode`);

--
-- Chỉ mục cho bảng `recorddetail`
--
ALTER TABLE `recorddetail`
  ADD PRIMARY KEY (`productID`,`recordID`);

--
-- Chỉ mục cho bảng `records`
--
ALTER TABLE `records`
  ADD PRIMARY KEY (`recordID`),
  ADD UNIQUE KEY `recordCode` (`recordCode`);

--
-- Chỉ mục cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`supplierID`),
  ADD UNIQUE KEY `supplierCode` (`supplierCode`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `customers`
--
ALTER TABLE `customers`
  MODIFY `customerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `records`
--
ALTER TABLE `records`
  MODIFY `recordID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `supplierID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
