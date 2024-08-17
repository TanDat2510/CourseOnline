-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: onlinecourse
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Kiến thức cơ sở','Các kiến thức nền tảng cần thiết','2024-08-11 05:24:23','2024-08-11 05:24:23'),(2,'Lập trình cơ sở','Những khái niệm và kỹ năng lập trình cơ bản','2024-08-11 05:24:23','2024-08-11 05:24:23'),(3,'Lập trình nâng cao','Những kỹ năng và kiến thức lập trình chuyên sâu','2024-08-11 05:24:23','2024-08-11 05:24:23'),(4,'Giải quyết vấn đề','Kỹ năng phân tích và giải quyết các vấn đề phức tạp','2024-08-11 05:24:23','2024-08-11 05:24:23'),(5,'Kỹ năng nâng cao','Các kỹ năng nâng cao để phát triển bản thân và sự nghiệp','2024-08-11 05:24:23','2024-08-11 05:24:23');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `time_experted` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price` bigint NOT NULL,
  `category_id` int NOT NULL,
  `instructor_id` int NOT NULL,
  `status` enum('ACTIVE','INACTIVE') COLLATE utf8mb3_unicode_ci NOT NULL,
  `course_type` enum('ONLINE','ON_OFF') COLLATE utf8mb3_unicode_ci NOT NULL,
  `img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_title` (`title`),
  KEY `category_id` (`category_id`),
  KEY `instructor_id` (`instructor_id`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Nhập môn Data Analysis','Khóa học cơ bản về phân tích dữ liệu sử dụng các công cụ phổ biến.','30 giờ','2024-08-12 06:51:21','2024-08-12 06:51:21',1200000,1,1,'ACTIVE','ONLINE',NULL),(2,'Triển khai Dịch vụ Đám mây','Hướng dẫn cách triển khai các dịch vụ đám mây với AWS.','40 giờ','2024-08-12 06:51:21','2024-08-12 06:51:21',1500000,2,2,'ACTIVE','ON_OFF',NULL),(3,'Xử lý Dữ liệu lớn','Kỹ thuật xử lý và phân tích dữ liệu lớn cho các dự án dữ liệu phức tạp.','50 giờ','2024-08-12 06:51:21','2024-08-12 06:51:21',1800000,3,1,'INACTIVE','ONLINE',NULL),(4,'Kỹ năng SQL nâng cao','Khóa học chuyên sâu về SQL cho quản lý và phân tích cơ sở dữ liệu.','35 giờ','2024-08-12 06:51:21','2024-08-12 06:51:21',1300000,4,2,'ACTIVE','ONLINE',NULL),(5,'Quản lý Dự án IT','Học cách quản lý các dự án IT từ khâu lập kế hoạch đến thực hiện.','45 giờ','2024-08-12 06:51:21','2024-08-12 06:51:21',1600000,5,1,'ACTIVE','ON_OFF',NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `expertise` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `instructor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES (1,'Data Analysis','Chuyên gia phân tích dữ liệu với khả năng sử dụng các công cụ phân tích mạnh mẽ.','2024-08-11 05:51:46','2024-08-11 05:51:46',4),(2,'Cloud Services','Chuyên gia về các dịch vụ đám mây, có kinh nghiệm triển khai ứng dụng trên AWS và Azure.','2024-08-11 05:51:46','2024-08-11 05:51:46',5);
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `last_name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `phone` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `active` bit(1) DEFAULT b'1',
  `user_role` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_phone` (`phone`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Đạt','Lê Tấn','tandat@gmail.com','0768812375','tandat','$2a$10$ubKTLEIaFKV8X8LisHX1YuuRp4i.drQYWVtE7aI.3oIC5La/0npdO',_binary '','ROLE_STUDENT','https://res.cloudinary.com/dh1irfap0/image/upload/v1723205644/d9wb2emq67gbbeayuojp.jpg','2024-08-09 12:14:03','2024-08-09 12:14:03'),(2,'Thắng','Nguyễn Việt','thang111103@gmail.com','0768812376','vietthang','$2a$10$5sl8HU6QP.AtIUKoyFVKq.jbTkoC7khGU/7eEbedAtmSAwPEh.8JG',_binary '','ROLE_ADMIN','https://res.cloudinary.com/dh1irfap0/image/upload/v1723205898/lq10cthjfd1cyvrftnr2.jpg','2024-08-09 12:18:17','2024-08-09 12:18:17'),(4,'A','Nguyễn Văn','nguyenvana@gmail.com','0768878945','vana','$2a$10$uOJcK3WC.Bj5XpUgc2pHBupKv4/vbS59/0MxO93tmN/NcbhLkvgCu',_binary '','ROLE_INSTRUCTOR','https://res.cloudinary.com/dh1irfap0/image/upload/v1723354887/vkpkpajqsfjrug9abbwr.jpg','2024-08-11 05:41:26','2024-08-11 05:41:26'),(5,'C','Nguyễn Văn','nguyenvanc@gmail.com','0768878946','vanc','$2a$10$zgwhmr3CBagHXE9xNEjq0OQ6rfPriqoW/lDC/t63z9WAdpjHA3f8i',_binary '','ROLE_INSTRUCTOR','https://res.cloudinary.com/dh1irfap0/image/upload/v1723355305/tfojy6rshv8wvwovw679.jpg','2024-08-11 05:48:23','2024-08-11 05:48:23'),(6,'D','Nguyễn Văn','nguyenvand@gmail.com','0768878347','vand','$2a$10$UyfPb1AcOm/aSFLVUpee8Op70t3H31mgGHQkXOmtbmk/opAZrgsd6',_binary '','ROLE_INSTRUCTOR','https://res.cloudinary.com/dh1irfap0/image/upload/v1723434096/n4lhczxhts5jlyqzspie.jpg','2024-08-12 03:41:37','2024-08-12 03:41:37'),(8,'E','Nguyễn Văn','nguyenvane@gmail.com','0768878341','vane','$2a$10$BEYKmaZm1swxstY/FlJybOmYqZvAnndJEkBUOElHubJgFmov.AXXC',_binary '','ROLE_INSTRUCTOR','https://res.cloudinary.com/dh1irfap0/image/upload/v1723472363/jd0yl4drowv5ux6kddok.jpg','2024-08-12 14:19:23','2024-08-12 14:19:23');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-12 22:09:49
