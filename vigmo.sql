-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 16 dec 2021 om 16:18
-- Serverversie: 10.4.16-MariaDB
-- PHP-versie: 7.4.12

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
-- Tabelstructuur voor tabel `availabilities`
--

CREATE TABLE `availabilities` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `week_day` tinyint(1) NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `consultation_hours`
--

CREATE TABLE `consultation_hours` (
  `id` bigint(20) NOT NULL,
  `description` varchar(220) DEFAULT NULL,
  `week_day` tinyint(1) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `logs`
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
-- Tabelstructuur voor tabel `media_slides`
--

CREATE TABLE `media_slides` (
  `id` bigint(20) NOT NULL,
  `audio_enabled` tinyint(1) NOT NULL DEFAULT 0,
  `type` varchar(50) NOT NULL,
  `resource` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rss_slides`
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
-- Tabelstructuur voor tabel `screens`
--

CREATE TABLE `screens` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `location` varchar(50) DEFAULT NULL,
  `auth_key` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `slides`
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
-- Tabelstructuur voor tabel `slideshows`
--

CREATE TABLE `slideshows` (
  `id` bigint(20) NOT NULL,
  `screen_id` bigint(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `slideshow_variables`
--

CREATE TABLE `slideshow_variables` (
  `id` bigint(20) NOT NULL,
  `slideshow_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(220) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `text_slides`
--

CREATE TABLE `text_slides` (
  `id` bigint(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
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
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `availabilities`
--
ALTER TABLE `availabilities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_availabilities_users_id` (`user_id`);

--
-- Indexen voor tabel `consultation_hours`
--
ALTER TABLE `consultation_hours`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_logs_users_id` (`user_id`);

--
-- Indexen voor tabel `media_slides`
--
ALTER TABLE `media_slides`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `rss_slides`
--
ALTER TABLE `rss_slides`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `screens`
--
ALTER TABLE `screens`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `slides`
--
ALTER TABLE `slides`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_slides_slideshows_id` (`slideshow_id`);

--
-- Indexen voor tabel `slideshows`
--
ALTER TABLE `slideshows`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_slideshows_screens_id` (`screen_id`);

--
-- Indexen voor tabel `slideshow_variables`
--
ALTER TABLE `slideshow_variables`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_slideshow_variables_slideshows_id` (`slideshow_id`);

--
-- Indexen voor tabel `text_slides`
--
ALTER TABLE `text_slides`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `availabilities`
--
ALTER TABLE `availabilities`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `consultation_hours`
--
ALTER TABLE `consultation_hours`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `logs`
--
ALTER TABLE `logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `media_slides`
--
ALTER TABLE `media_slides`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `rss_slides`
--
ALTER TABLE `rss_slides`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `screens`
--
ALTER TABLE `screens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `slides`
--
ALTER TABLE `slides`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `slideshows`
--
ALTER TABLE `slideshows`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `slideshow_variables`
--
ALTER TABLE `slideshow_variables`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `text_slides`
--
ALTER TABLE `text_slides`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `availabilities`
--
ALTER TABLE `availabilities`
  ADD CONSTRAINT `fk_availabilities_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `fk_logs_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `media_slides`
--
ALTER TABLE `media_slides`
  ADD CONSTRAINT `fk_slides_media_slides_id	` FOREIGN KEY (`id`) REFERENCES `slides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `rss_slides`
--
ALTER TABLE `rss_slides`
  ADD CONSTRAINT `fk_slides_rss_slides_id` FOREIGN KEY (`id`) REFERENCES `slides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `slides`
--
ALTER TABLE `slides`
  ADD CONSTRAINT `fk_slides_slideshows_id` FOREIGN KEY (`slideshow_id`) REFERENCES `slideshows` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `slideshows`
--
ALTER TABLE `slideshows`
  ADD CONSTRAINT `fk_slideshows_screens_id` FOREIGN KEY (`screen_id`) REFERENCES `screens` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `slideshow_variables`
--
ALTER TABLE `slideshow_variables`
  ADD CONSTRAINT `fk_slideshow_variables_slideshows_id` FOREIGN KEY (`slideshow_id`) REFERENCES `slideshows` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `text_slides`
--
ALTER TABLE `text_slides`
  ADD CONSTRAINT `fk_slides_text_slides_id	` FOREIGN KEY (`id`) REFERENCES `slides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
