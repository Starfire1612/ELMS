CREATE DATABASE  IF NOT EXISTS `elms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `elms`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: elms
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `certificate`
--

DROP TABLE IF EXISTS `certificate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certificate` (
  `cert_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NOT NULL,
  `stu_id` int NOT NULL,
  `cert_issue_date` date NOT NULL,
  `cert_link` mediumtext NOT NULL,
  PRIMARY KEY (`cert_id`),
  KEY `course_id_idx` (`course_id`),
  KEY `stu_id_idx` (`stu_id`),
  CONSTRAINT `cert_course_id` FOREIGN KEY (`course_id`) REFERENCES `student_course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cert_stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student_course` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (1,1,1,'2022-01-01','https://www.google.com'),(2,2,2,'2022-02-20','https://www.google.com'),(3,3,3,'2022-01-03','https://www.google.com');
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `course_description` longtext NOT NULL,
  `course_image` blob,
  `course_duration` int NOT NULL,
  `course_price` decimal(10,0) NOT NULL,
  `course_discount_percent` decimal(10,0) DEFAULT NULL,
  `course_published` date NOT NULL,
  `course_ratings` int DEFAULT NULL,
  `instructor_id` int NOT NULL,
  `instructor_name` varchar(100) NOT NULL,
  `total_lessons` int NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_name_UNIQUE` (`course_name`),
  KEY `instructor_id_idx` (`instructor_id`),
  CONSTRAINT `instructor_id` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'Html','beginner to advanced',NULL,1,1000,10,'2021-05-09',5,1,'Ravi',2),(2,'Css','beginner to advanced',NULL,3,1000,10,'2021-09-09',4,2,'Ram',2),(3,'JS','beginner to advanced',NULL,5,2000,10,'2021-02-09',5,3,'Sham',2),(4,'React','beginner to advanced',NULL,7,3000,10,'2021-06-09',3,1,'Ravi',2),(5,'Spring','beginner to advanced',NULL,7,2000,10,'2021-06-05',4,2,'Ram',2),(6,'Spring Boot','beginner to advanced',NULL,7,2000,10,'2021-06-09',5,3,'Sham',0);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `stu_id` int NOT NULL,
  `course_id` int NOT NULL,
  `feedback_content` text,
  `feedback_ratings` int NOT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `stu_id_idx` (`stu_id`),
  KEY `course_id_idx` (`course_id`),
  CONSTRAINT `feedback_course_id` FOREIGN KEY (`course_id`) REFERENCES `student_course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `feedback_stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student_course` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,1,1,'hi',4),(2,2,2,'good',4),(3,3,3,'best',5);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `instructor_id` int NOT NULL AUTO_INCREMENT,
  `instructor_name` varchar(150) NOT NULL,
  `instructor_email` varchar(100) NOT NULL,
  `instructor_password` varchar(30) NOT NULL,
  `instructor_image` blob,
  `bank_ifsc_code` varchar(20) NOT NULL,
  `account_number` bigint NOT NULL,
  PRIMARY KEY (`instructor_id`),
  UNIQUE KEY `instructor_email_UNIQUE` (`instructor_email`),
  UNIQUE KEY `instructor_password_UNIQUE` (`instructor_password`),
  UNIQUE KEY `account_number_UNIQUE` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES (1,'Ravi','ravi@gmail.com','helloravi',NULL,'HDFC0001433',59183912839281),(2,'Ram','ram@gmail.com','helloram',NULL,'HDFC0001433',59188118238949),(3,'Sham','sham@gmail.com','hellosham',NULL,'HDFC0001433',591928157391273);
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_course`
--

DROP TABLE IF EXISTS `instructor_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_course` (
  `instructor_id` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`instructor_id`,`course_id`),
  KEY `course_id_idx` (`course_id`),
  CONSTRAINT `ic_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ic_instructor_id` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`instructor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_course`
--

LOCK TABLES `instructor_course` WRITE;
/*!40000 ALTER TABLE `instructor_course` DISABLE KEYS */;
INSERT INTO `instructor_course` VALUES (1,1),(2,2),(3,3),(1,4),(2,5),(3,6);
/*!40000 ALTER TABLE `instructor_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
  `lesson_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NOT NULL,
  `lesson_name` varchar(255) NOT NULL,
  `lesson_duration` int NOT NULL,
  `lesson_link` text NOT NULL,
  PRIMARY KEY (`lesson_id`),
  KEY `lesson_course_id_idx` (`course_id`),
  CONSTRAINT `lesson_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,1,'Java',1,'https://youtu.be/Chq1DfCQ-Dg'),(2,2,'Spring Core',2,'https://youtu.be/Chq1DfCQ-Dg'),(3,3,'Spring MVC',3,'https://youtu.be/Chq1DfCQ-Dg'),(4,4,'Spring Boot',6,'https://youtu.be/Chq1DfCQ-Dg'),(5,5,'React',1,'https://youtu.be/Chq1DfCQ-Dg'),(6,1,'HTML,CSS,JS',12,'https://youtu.be/Chq1DfCQ-Dg'),(7,2,'Python',6,'https://youtu.be/Chq1DfCQ-Dg'),(8,3,'Microservices',16,'https://youtu.be/Chq1DfCQ-Dg'),(9,4,'AWS',12,'https://youtu.be/Chq1DfCQ-Dg'),(10,5,'Spring Rest API',14,'https://youtu.be/Chq1DfCQ-Dg');
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `stu_id` int NOT NULL,
  `course_id` int NOT NULL,
  `payment_date` date NOT NULL,
  `payment_amount` decimal(10,0) NOT NULL,
  `payment_response_message` text NOT NULL,
  `payment_status` text NOT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `course_id_idx` (`course_id`),
  KEY `stu_id_idx` (`stu_id`),
  CONSTRAINT `payment_course_id` FOREIGN KEY (`course_id`) REFERENCES `student_course` (`course_id`),
  CONSTRAINT `payment_stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student_course` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,1,'2021-10-10',499,'hi','Success'),(2,2,2,'2021-12-02',405,'good','Fail');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `stu_id` int NOT NULL AUTO_INCREMENT,
  `stu_name` varchar(150) NOT NULL,
  `stu_email` varchar(70) NOT NULL,
  `stu_password` varchar(30) NOT NULL,
  `stu_image` blob,
  PRIMARY KEY (`stu_id`),
  UNIQUE KEY `stu_email_UNIQUE` (`stu_email`),
  UNIQUE KEY `stu_password_UNIQUE` (`stu_password`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Prateek','prateek@gmail.com','helloprateek',_binary 'null'),(2,'Shubham','shubham@gmail.com','helloshubham',_binary 'null'),(3,'Anurag','anurag@gmail.com','helloanurag',_binary 'null'),(4,'Radhika','radhika@gmail.com','helloradhika',_binary 'null');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_course`
--

DROP TABLE IF EXISTS `student_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_course` (
  `stu_id` int NOT NULL,
  `course_id` int NOT NULL,
  `course_status` varchar(45) NOT NULL,
  `course_completion_percent` int NOT NULL,
  `current_lesson_id` int NOT NULL,
  PRIMARY KEY (`stu_id`,`course_id`),
  KEY `course_id_idx` (`course_id`),
  KEY `current_lesson_id_idx` (`current_lesson_id`),
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `current_lesson_id` FOREIGN KEY (`current_lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_course`
--

LOCK TABLES `student_course` WRITE;
/*!40000 ALTER TABLE `student_course` DISABLE KEYS */;
INSERT INTO `student_course` VALUES (1,1,'Pending',50,1),(1,4,'Pending',30,9),(2,2,'Pending',60,2),(3,3,'Completed',100,8);
/*!40000 ALTER TABLE `student_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_course_lesson`
--

DROP TABLE IF EXISTS `student_course_lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_course_lesson` (
  `stu_id` int NOT NULL,
  `course_id` int NOT NULL,
  `lesson_id` int NOT NULL,
  PRIMARY KEY (`stu_id`,`course_id`,`lesson_id`),
  KEY `course_id_idx` (`course_id`),
  KEY `leeson_id_idx` (`lesson_id`),
  CONSTRAINT `scl_course_id` FOREIGN KEY (`course_id`) REFERENCES `student_course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scl_leeson_id` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scl_stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student_course` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_course_lesson`
--

LOCK TABLES `student_course_lesson` WRITE;
/*!40000 ALTER TABLE `student_course_lesson` DISABLE KEYS */;
INSERT INTO `student_course_lesson` VALUES (1,1,1),(1,1,6),(2,2,2),(2,2,7),(3,3,3),(3,3,8),(1,4,4),(1,4,9);
/*!40000 ALTER TABLE `student_course_lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'elms'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-06 13:31:46
