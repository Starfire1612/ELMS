#creating the database

create database elms;

#using the database
use elms;

#creating the student table
CREATE TABLE `elms`.`student` (
  `stu_id` INT NOT NULL AUTO_INCREMENT,
  `stu_name` VARCHAR(150) NOT NULL,
  `stu_email` VARCHAR(70) NOT NULL,
  `stu_password` VARCHAR(30) NOT NULL,
  `stu_image` BLOB NOT NULL,
  PRIMARY KEY (`stu_id`),
  UNIQUE INDEX `stu_email_UNIQUE` (`stu_email` ASC) VISIBLE,
  UNIQUE INDEX `stu_password_UNIQUE` (`stu_password` ASC) VISIBLE);

#creating the instructor table
CREATE TABLE `elms`.`instructor` (
  `instructor_id` INT NOT NULL auto_increment,
  `instructor_name` VARCHAR(150) NOT NULL,
  `instructor_email` VARCHAR(100) NOT NULL,
  `instructor_password` VARCHAR(30) NOT NULL,
  `instructor_image` BLOB NOT NULL,
  `bank_ifsc_code` VARCHAR(20) NOT NULL,
  `account_number` BIGINT(20) NOT NULL,
  PRIMARY KEY (`instructor_id`),
  UNIQUE INDEX `instructor_email_UNIQUE` (`instructor_email` ASC) VISIBLE,
  UNIQUE INDEX `instructor_password_UNIQUE` (`instructor_password` ASC) VISIBLE,
  UNIQUE INDEX `account_number_UNIQUE` (`account_number` ASC) VISIBLE);

#creating the course table
CREATE TABLE `elms`.`course` (
  `course_id` INT NOT NULL auto_increment,
  `course_name` VARCHAR(255) NOT NULL,
  `course_description` LONGTEXT NOT NULL,
  `course_image` BLOB NOT NULL,
  `course_duration` INT(7) NOT NULL,
  `course_price` DECIMAL NOT NULL,
  `course_discount_percent` DECIMAL NULL,
  `course_published` DATE NOT NULL,
  `course_ratings` INT(1) NULL,
  `instructor_id` INT NOT NULL,
  `instructor_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE INDEX `course_name_UNIQUE` (`course_name` ASC) VISIBLE,
  INDEX `instructor_id_idx` (`instructor_id` ASC) VISIBLE,
  CONSTRAINT `instructor_id`
    FOREIGN KEY (`instructor_id`)
    REFERENCES `elms`.`instructor` (`instructor_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

# creating the course_student table
CREATE TABLE `elms`.`student_course` (
  `stu_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `course_status` VARCHAR(45) NOT NULL,
  `course_completion_percent` INT NOT NULL,
  PRIMARY KEY (`stu_id`, `course_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `stu_id`
    FOREIGN KEY (`stu_id`)
    REFERENCES `elms`.`student` (`stu_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`course` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

#creating the certificate table
CREATE TABLE `elms`.`certificate` (
  `cert_id` INT NOT NULL auto_increment,
  `course_id` INT NOT NULL,
  `stu_id` INT NOT NULL,
  `cert_issue_date` DATE NOT NULL,
  `cert_link` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`cert_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  INDEX `stu_id_idx` (`stu_id` ASC) VISIBLE,
  CONSTRAINT `cert_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`student_course` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cert_stu_id`
    FOREIGN KEY (`stu_id`)
    REFERENCES `elms`.`student_course` (`stu_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

#creating the feedback table
CREATE TABLE `elms`.`feedback` (
  `feedback_id` INT NOT NULL auto_increment,
  `stu_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `feedback_content` TEXT NULL,
  `feedback_ratings` INT(1) NOT NULL,
  PRIMARY KEY (`feedback_id`),
  INDEX `stu_id_idx` (`stu_id` ASC) VISIBLE,
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `feedback_stu_id`
    FOREIGN KEY (`stu_id`)
    REFERENCES `elms`.`student_course` (`stu_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `feedback_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`student_course` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

#creating the payment table

CREATE TABLE `elms`.`payment` (
  `payment_id` INT NOT NULL auto_increment,
  `stu_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `payment_date` DATE NOT NULL,
  `payment_amount` DECIMAL NOT NULL,
  `payment_response_message` TEXT NOT NULL,
  `payment_status` TEXT NOT NULL,
  PRIMARY KEY (`payment_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  INDEX `stu_id_idx` (`stu_id` ASC) VISIBLE,
  CONSTRAINT `payment_stu_id`
    FOREIGN KEY (`stu_id`)
    REFERENCES `elms`.`student_course` (`stu_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `payment_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`student_course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
#creating the lesson table
CREATE TABLE `elms`.`lesson` (
  `lesson_id` INT NOT NULL auto_increment,
  `course_id` INT NOT NULL,
  `lesson_name` VARCHAR(255) NOT NULL,
  `lesson_duration` INT NOT NULL,
  `lesson_link` TEXT NOT NULL,
  PRIMARY KEY (`lesson_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `lesson_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`student_course` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

#creating instructor_course table
CREATE TABLE `elms`.`instructor_course` (
  `instructor_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`instructor_id`, `course_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `ic_instructor_id`
    FOREIGN KEY (`instructor_id`)
    REFERENCES `elms`.`instructor` (`instructor_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ic_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`course` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `elms`.`student_course_lesson` (
  `stu_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `lesson_id` INT NOT NULL,
  PRIMARY KEY (`stu_id`, `course_id`, `lesson_id`),
  INDEX `course_id_idx` (`course_id` ASC) VISIBLE,
  INDEX `leeson_id_idx` (`lesson_id` ASC) VISIBLE,
  CONSTRAINT `scl_stu_id`
    FOREIGN KEY (`stu_id`)
    REFERENCES `elms`.`student_course` (`stu_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `scl_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `elms`.`student_course` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `scl_leeson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `elms`.`lesson` (`lesson_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

#altering student_course table
ALTER TABLE `elms`.`student_course` 
ADD COLUMN `current_lesson_id` INT NOT NULL AFTER `course_completion_percent`,
ADD INDEX `current_lesson_id_idx` (`current_lesson_id` ASC) VISIBLE;

#altering student_course table
ALTER TABLE `elms`.`student_course` 
ADD CONSTRAINT `current_lesson_id`
  FOREIGN KEY (`current_lesson_id`)
  REFERENCES `elms`.`lesson` (`lesson_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

#altering course table
ALTER TABLE `elms`.`course` 
ADD COLUMN `total_lessons` INT NOT NULL AFTER `instructor_name`;
