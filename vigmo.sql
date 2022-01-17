-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 14, 2022 at 04:40 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vigmo`
--

-- --------------------------------------------------------

--
-- Table structure for table `availabilities`
--

CREATE TABLE `availabilities` (
                                  `id` bigint(20) NOT NULL,
                                  `user_id` bigint(20) NOT NULL,
                                  `week_day` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY') NOT NULL,
                                  `start_time` time DEFAULT NULL,
                                  `end_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `consultation_hours`
--

CREATE TABLE `consultation_hours` (
                                      `id` bigint(20) NOT NULL,
                                      `description` varchar(220) DEFAULT NULL,
                                      `week_day` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY') NOT NULL,
                                      `start_time` time NOT NULL,
                                      `end_time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `databasechangelog`
--

CREATE TABLE `databasechangelog` (
                                     `ID` varchar(255) NOT NULL,
                                     `AUTHOR` varchar(255) NOT NULL,
                                     `FILENAME` varchar(255) NOT NULL,
                                     `DATEEXECUTED` datetime NOT NULL,
                                     `ORDEREXECUTED` int(11) NOT NULL,
                                     `EXECTYPE` varchar(10) NOT NULL,
                                     `MD5SUM` varchar(35) DEFAULT NULL,
                                     `DESCRIPTION` varchar(255) DEFAULT NULL,
                                     `COMMENTS` varchar(255) DEFAULT NULL,
                                     `TAG` varchar(255) DEFAULT NULL,
                                     `LIQUIBASE` varchar(20) DEFAULT NULL,
                                     `CONTEXTS` varchar(255) DEFAULT NULL,
                                     `LABELS` varchar(255) DEFAULT NULL,
                                     `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
                                         `ID` int(11) NOT NULL,
                                         `LOCKED` bit(1) NOT NULL,
                                         `LOCKGRANTED` datetime DEFAULT NULL,
                                         `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `files`
--

CREATE TABLE `files` (
                         `id` bigint(20) NOT NULL,
                         `file_name` varchar(220) NOT NULL,
                         `mime_type` varchar(100) NOT NULL,
                         `file` mediumblob NOT NULL COMMENT '16mb',
                         `file_key` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
                        `id` bigint(20) NOT NULL,
                        `user_id` bigint(20) DEFAULT NULL,
                        `username` varchar(50) NOT NULL,
                        `action` varchar(50) NOT NULL,
                        `message` varchar(220) DEFAULT NULL,
                        `datetime` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `media_slides`
--

CREATE TABLE `media_slides` (
                                `id` bigint(20) NOT NULL,
                                `audio_enabled` tinyint(1) NOT NULL DEFAULT 0,
                                `type` varchar(50) NOT NULL,
                                `resource` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `rss_slides`
--

CREATE TABLE `rss_slides` (
                              `id` bigint(20) NOT NULL,
                              `url` varchar(220) NOT NULL,
                              `title_tag` varchar(50) DEFAULT NULL,
                              `description_tag` varchar(50) DEFAULT NULL,
                              `author_tag` varchar(50) DEFAULT NULL,
                              `category_tag` varchar(50) DEFAULT NULL,
                              `image_tag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `screens`
--

CREATE TABLE `screens` (
                           `id` bigint(20) NOT NULL,
                           `name` varchar(50) NOT NULL,
                           `location` varchar(50) DEFAULT NULL,
                           `auth_key` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `slides`
--

CREATE TABLE `slides` (
                          `id` bigint(20) NOT NULL,
                          `slideshow_id` bigint(20) NOT NULL,
                          `is_active` tinyint(1) NOT NULL,
                          `duration` int(11) NOT NULL DEFAULT 5,
                          `start_date` date DEFAULT NULL,
                          `end_date` date DEFAULT NULL,
                          `start_time` time DEFAULT NULL,
                          `end_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `slideshows`
--

CREATE TABLE `slideshows` (
                              `id` bigint(20) NOT NULL,
                              `screen_id` bigint(20) DEFAULT NULL,
                              `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `slideshow_variables`
--

CREATE TABLE `slideshow_variables` (
                                       `id` bigint(20) NOT NULL,
                                       `slideshow_id` bigint(20) NOT NULL,
                                       `name` varchar(50) NOT NULL,
                                       `value` varchar(220) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `text_slides`
--

CREATE TABLE `text_slides` (
                               `id` bigint(20) NOT NULL,
                               `title` varchar(50) NOT NULL,
                               `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL,
                         `username` varchar(50) NOT NULL,
                         `password` varchar(220) NOT NULL,
                         `enabled` tinyint(1) NOT NULL DEFAULT 1,
                         `role` varchar(50) NOT NULL DEFAULT 'ROLE_DOCENT',
                         `pfp_location` varchar(220) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `availabilities`
--
ALTER TABLE `availabilities`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_availabilities_users_id` (`user_id`);

--
-- Indexes for table `consultation_hours`
--
ALTER TABLE `consultation_hours`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
    ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `files`
--
ALTER TABLE `files`
    ADD PRIMARY KEY (`id`),
  ADD KEY `file_key` (`file_key`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_logs_users_id` (`user_id`);

--
-- Indexes for table `media_slides`
--
ALTER TABLE `media_slides`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rss_slides`
--
ALTER TABLE `rss_slides`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `screens`
--
ALTER TABLE `screens`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `slides`
--
ALTER TABLE `slides`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_slides_slideshows_id` (`slideshow_id`);

--
-- Indexes for table `slideshows`
--
ALTER TABLE `slideshows`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_slideshows_screens_id` (`screen_id`);

--
-- Indexes for table `slideshow_variables`
--
ALTER TABLE `slideshow_variables`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_slideshow_variables_slideshows_id` (`slideshow_id`);

--
-- Indexes for table `text_slides`
--
ALTER TABLE `text_slides`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `availabilities`
--
ALTER TABLE `availabilities`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consultation_hours`
--
ALTER TABLE `consultation_hours`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `files`
--
ALTER TABLE `files`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `media_slides`
--
ALTER TABLE `media_slides`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rss_slides`
--
ALTER TABLE `rss_slides`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `screens`
--
ALTER TABLE `screens`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `slides`
--
ALTER TABLE `slides`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `slideshows`
--
ALTER TABLE `slideshows`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `slideshow_variables`
--
ALTER TABLE `slideshow_variables`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `text_slides`
--
ALTER TABLE `text_slides`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `availabilities`
--
ALTER TABLE `availabilities`
    ADD CONSTRAINT `fk_availabilities_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
    ADD CONSTRAINT `fk_logs_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `media_slides`
--
ALTER TABLE `media_slides`
    ADD CONSTRAINT `fk_slides_media_slides_id	` FOREIGN KEY (`id`) REFERENCES `slides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rss_slides`
--
ALTER TABLE `rss_slides`
    ADD CONSTRAINT `fk_slides_rss_slides_id` FOREIGN KEY (`id`) REFERENCES `slides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `slides`
--
ALTER TABLE `slides`
    ADD CONSTRAINT `fk_slides_slideshows_id` FOREIGN KEY (`slideshow_id`) REFERENCES `slideshows` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `slideshows`
--
ALTER TABLE `slideshows`
    ADD CONSTRAINT `fk_slideshows_screens_id` FOREIGN KEY (`screen_id`) REFERENCES `screens` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `slideshow_variables`
--
ALTER TABLE `slideshow_variables`
    ADD CONSTRAINT `fk_slideshow_variables_slideshows_id` FOREIGN KEY (`slideshow_id`) REFERENCES `slideshows` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `text_slides`
--
ALTER TABLE `text_slides`
    ADD CONSTRAINT `fk_slides_text_slides_id	` FOREIGN KEY (`id`) REFERENCES `slides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
