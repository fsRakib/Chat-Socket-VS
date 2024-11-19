-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 19, 2024 at 05:16 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chatapp2`
--

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `sender` varchar(50) DEFAULT NULL,
  `receiver` varchar(50) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`id`, `sender`, `receiver`, `content`, `timestamp`) VALUES
(2, 'user2', 'user1', 'hello rakib', '2024-06-21 14:58:47'),
(3, 'user3', 'user2', 'hi Arka', '2024-06-21 14:59:39'),
(4, 'user4', 'user3', 'hi morshed', '2024-06-21 15:00:50'),
(5, 'user1', 'user1', 'fine', '2024-06-21 15:01:04'),
(6, 'user4', 'user4', 'fine', '2024-06-21 15:01:14'),
(7, 'user3', 'user1', 'koi jas', '2024-06-21 15:01:34'),
(8, 'Rakib', 'Arka', 'Hi arka. ki koros?', '2024-06-22 12:17:46'),
(9, 'Arka', 'Rakib', 'Kisui na.', '2024-06-22 12:18:03'),
(10, 'Arka', 'Rakib', 'tui koi?', '2024-06-22 12:18:11'),
(11, 'Rakib', 'Arka', 'ami sylhet', '2024-06-22 12:18:47'),
(12, 'Arka', 'Rakib', 'oh. tor sahthe dekha holo na', '2024-06-22 12:19:11'),
(14, 'Arka', 'Morshed', 'koi tui', '2024-06-22 12:20:15'),
(15, 'Morshed', 'Arka', 'kachkuray', '2024-06-22 12:20:28'),
(16, 'Rakib', 'Morshed', 'school e dike ay', '2024-06-22 12:20:45'),
(17, 'Morshed', 'Rakib', 'exam ase', '2024-06-22 12:21:08'),
(18, 'Rakib', 'Arka', 'hi', '2024-06-22 12:41:31'),
(19, 'Arka', 'Rakib', 'hello', '2024-06-22 12:41:41'),
(20, 'Arka', 'Morshed', 'koi?', '2024-06-22 12:41:54'),
(21, 'Rakib', 'Arka', 'aosos?', '2024-06-22 12:42:05'),
(22, 'Rakib', 'Arka', 'hi', '2024-06-22 13:21:19'),
(23, 'Arka', 'Rakib', 'koi asos?', '2024-06-22 13:21:30'),
(24, 'Rakib', 'Arka', 'hi', '2024-06-22 13:26:06'),
(25, 'Arka', 'Rakib', 'hello', '2024-06-22 13:26:27'),
(26, 'Rakib', 'Arka', 'hi', '2024-06-22 13:31:14'),
(27, 'Arka', 'Rakib', 'ki koros?', '2024-06-22 13:31:35'),
(28, 'Morshed', 'Arka', 'kire phone dhor', '2024-06-22 13:33:17'),
(29, 'Arka', 'Rakib', 'tui elakay ay.', '2024-06-22 13:33:49'),
(30, 'Rakib', 'Arka', 'ho morshe. aqy', '2024-06-22 13:34:00'),
(31, 'Morshed', 'Arka', 'amar exam', '2024-06-22 13:34:12'),
(32, 'Rakib', 'Morshed', 'ho morshed ay', '2024-06-22 13:34:24'),
(33, 'Morshed', 'Rakib', 'exam er pora pori', '2024-06-22 13:35:13'),
(34, 'Arka', 'Morshed', 'mara kha', '2024-06-22 13:35:27'),
(35, 'Morshed', 'Rakib', 'hi', '2024-06-22 13:53:29'),
(36, 'Rakib', 'Morshed', 'ki koros?', '2024-06-22 13:53:47'),
(37, 'Morshed', 'Rakib', 'pori', '2024-06-22 13:53:54'),
(38, 'Arka', 'Rakib', 'koi asos?', '2024-06-22 13:54:20'),
(39, 'Arka', 'Morshed', 'basay ay..', '2024-06-22 13:54:35'),
(40, 'Morshed', 'Arka', 'na', '2024-06-22 13:55:00'),
(41, 'Rakib', 'Arka', 'sylhet', '2024-06-22 13:55:10'),
(42, 'Rakib', 'Arka', 'hi', '2024-06-22 14:37:00'),
(43, 'Arka', 'Rakib', 'hello', '2024-06-22 14:37:07'),
(44, 'Morshed', 'Rakib', 'hi', '2024-06-22 14:37:57'),
(45, 'Morshed', 'Arka', 'hi', '2024-06-22 14:38:01'),
(46, 'Arka', 'Rakib', 'kooi', '2024-06-22 14:38:26'),
(47, 'Rakib', 'Arka', 'asi', '2024-06-22 14:38:30'),
(48, 'Rakib', 'Morshed', 'ber ho', '2024-06-22 14:38:39'),
(49, 'Arka', 'Morshed', 'ber ho', '2024-06-22 14:38:45'),
(50, 'Morshed', 'Arka', 'ok', '2024-06-22 14:38:51'),
(51, 'Morshed', 'Rakib', 'ok', '2024-06-22 14:39:08'),
(52, 'Arka', 'Rakib', 'hi', '2024-06-22 14:43:01'),
(53, 'Arka', 'Morshed', 'hi', '2024-06-22 14:43:06'),
(54, 'Rakib', 'Arka', 'hi', '2024-06-22 14:43:16'),
(55, 'Rakib', 'Morshed', 'hi', '2024-06-22 14:43:19'),
(56, 'Morshed', 'Arka', 'hi', '2024-06-22 14:43:25'),
(57, 'Morshed', 'Rakib', 'hi', '2024-06-22 14:43:29'),
(58, 'Siam', 'Arka', 'hi', '2024-06-22 14:55:56'),
(59, 'Arka', 'Siam', 'hello', '2024-06-22 14:56:05'),
(60, 'Arka', 'Siam', 'hoi asos', '2024-06-22 14:56:22'),
(61, 'Siam', 'Arka', 'basday', '2024-06-22 14:56:27'),
(62, 'Arka', 'Rakib', 'hi', '2024-06-22 15:04:01'),
(63, 'Rakib', 'Arka', 'koi', '2024-06-22 15:04:12'),
(64, 'Arka', 'Rakib', 'chol jai', '2024-06-22 15:07:17'),
(65, 'Rakib', 'Arka', 'kothay?', '2024-06-22 15:07:29'),
(66, 'Arka', 'Siam', 'barite?', '2024-06-22 15:07:43'),
(67, 'Siam', 'Arka', 'hmm', '2024-06-22 15:07:52'),
(68, 'Arka', 'Siam', 'basay ay', '2024-06-22 15:08:35'),
(69, 'Arka', 'Rakib', 'hi', '2024-06-22 15:10:12'),
(70, 'Arka', 'Siam', 'hi', '2024-06-22 15:10:29'),
(71, 'Siam', 'Rakib', 'hello', '2024-06-22 15:10:41'),
(72, 'Siam', 'Arka', 'hi', '2024-06-22 15:10:46'),
(73, 'Rakib', 'Arka', 'hi', '2024-06-22 15:10:57'),
(74, 'Rakib', 'Siam', 'hi', '2024-06-22 15:11:00'),
(75, 'Rakib', 'Arka', 'hi', '2024-06-22 16:21:19'),
(76, 'Arka', 'Rakib', 'hello', '2024-06-22 16:21:30'),
(77, 'Arka', 'Rakib', 'hi', '2024-06-22 16:40:50'),
(78, 'Rakib', 'Arka', 'hi', '2024-06-22 16:53:32'),
(79, 'Arka', 'Rakib', 'hi', '2024-06-22 16:53:39'),
(80, 'Rakib', 'Arka', 'hi', '2024-06-22 16:58:48'),
(81, 'Arka', 'Rakib', 'hello', '2024-06-22 16:58:57'),
(82, 'Rakib', 'Arka', 'hi', '2024-06-22 17:08:26'),
(83, 'Arka', 'Rakib', 'hi', '2024-06-22 17:10:34'),
(84, 'Sawon', 'Arka', 'hi', '2024-06-22 17:27:24'),
(85, 'Arka', 'Sawon', 'hello', '2024-06-22 17:27:34'),
(86, 'Arka', 'Rakib', 'hi', '2024-06-22 17:39:52'),
(87, 'Rakib', 'Arka', 'hi', '2024-06-22 18:10:36'),
(88, 'Arka', 'Rakib', 'hello', '2024-06-22 18:10:45'),
(89, 'Arka', 'Rakib', 'hi', '2024-06-22 18:45:35'),
(90, 'Rakib', 'Sawon', 'hi', '2024-06-22 18:46:27'),
(91, 'Sawon', 'Rakib', 'hello', '2024-06-22 18:46:44'),
(92, 'Rakib', 'Arka', 'koi', '2024-06-22 18:46:58'),
(93, 'Arka', 'Sawon', 'hi', '2024-06-22 18:47:04'),
(94, 'Arka', 'Rakib', 'asi', '2024-06-22 18:47:09'),
(95, 'Arka', 'Rakib', 'hi ', '2024-06-22 19:02:04'),
(96, 'Rakib', 'Arka', 'hello', '2024-06-22 19:02:19'),
(97, 'Morshed', 'Arka', 'hi', '2024-06-22 19:05:15'),
(98, 'Rakib', 'Morshed', 'hi', '2024-06-22 19:07:59'),
(99, 'Arka', 'Rakib', 'hi', '2024-06-22 19:08:44'),
(100, 'Morshed', 'Rakib', 'hio', '2024-06-22 19:08:50'),
(101, 'Emon', 'Rakib', 'hi', '2024-06-22 19:24:02'),
(102, 'Arka', 'Rakib', 'hi', '2024-06-22 19:30:02'),
(103, 'Rakib', 'Arka', 'hi', '2024-06-22 19:32:29'),
(104, 'Arka', 'Rakib', 'hi', '2024-06-22 19:36:55'),
(105, 'Rakib', 'Arka', 'hi', '2024-06-22 19:39:02'),
(106, 'Arka', 'Rakib', 'hello', '2024-06-22 19:39:20'),
(107, 'Arka', 'Emon', 'hi', '2024-06-22 20:14:15'),
(108, 'Emon', 'Arka', 'hi', '2024-06-22 20:16:24'),
(109, 'Rakib', 'Arka', 'hello', '2024-06-22 20:16:31'),
(110, 'Arka', 'Emon', 'hoi asos', '2024-06-22 20:16:38'),
(111, 'Emon', 'Rakib', 'asi', '2024-06-22 20:16:55'),
(112, 'Morshed', 'Arka', 'hi', '2024-06-22 20:53:00'),
(113, 'Arka', 'Morshed', 'hello', '2024-06-22 20:53:12'),
(114, 'Emon', 'Arka', 'hi', '2024-06-22 20:53:31'),
(115, 'Arka', 'Emon', 'hello', '2024-06-22 20:53:42'),
(116, 'Emon', 'Morshed', 'hi', '2024-06-22 20:53:51'),
(117, 'Morshed', 'Emon', 'ki', '2024-06-22 20:54:00'),
(118, 'Arka', 'Rakib', 'hi', '2024-06-23 09:41:25'),
(119, 'Rakib', 'Arka', 'hello', '2024-06-23 09:41:54'),
(120, 'Rakib', 'Emon', 'hi', '2024-06-23 09:42:33'),
(121, 'Arka', 'Rakib', 'hi', '2024-06-23 09:59:56'),
(122, 'Rakib', 'Arka', 'ge', '2024-06-23 11:45:12'),
(123, 'Arka', 'Rakib', 'fgegfsdg', '2024-06-23 11:45:23'),
(124, 'Rakib', 'Emon', 'hi', '2024-06-23 11:56:24'),
(125, 'Emon', 'Rakib', 'helloo', '2024-06-23 11:56:31'),
(126, 'Arka', 'Rakib', 'hi', '2024-06-23 12:37:14'),
(127, 'Arka', 'Rakib', 'etewrt', '2024-10-21 15:54:42'),
(128, 'Rakib', 'Arka', 'aefsdfsdfsrgsfgsdf', '2024-10-21 15:54:50'),
(129, 'Rakib', 'Arka', 'dg', '2024-10-21 16:32:26'),
(130, 'Arka', 'Rakib', 'EGDEG', '2024-10-21 16:32:57'),
(131, 'Rakib', 'Arka', 'EGERGERDG', '2024-10-21 16:33:07'),
(132, 'Arka', 'Rakib', 'WTEWRTERTFER', '2024-10-21 16:33:14'),
(133, 'Rakib', 'Arka', 'xfgdfxv', '2024-10-21 16:34:22'),
(134, 'Arka', 'Rakib', 'vxdfvdxvdxfvdx', '2024-10-21 16:34:26'),
(135, 'Rakib', 'Arka', 'fbdfbgdfbdcfb', '2024-10-21 16:34:29'),
(136, 'Arka', 'Rakib', 'fbdfcbdfcbdfcxb', '2024-10-21 16:34:31'),
(137, 'Arka', 'Rakib', 'ghfgh', '2024-10-21 16:46:01'),
(138, 'Rakib', 'Arka', 'fghfghfghfgh', '2024-10-21 16:46:05'),
(139, 'Ferxin2', 'Arka', 'fgdrgdg', '2024-10-21 16:47:37'),
(140, 'Arka', 'Rakib', 'dfgdfgdfgdf', '2024-10-21 16:47:40'),
(141, 'Arka', 'Rakib', 'dgdgdfgdfg', '2024-10-21 16:47:45'),
(142, 'Ferxin2', 'Arka', 'dfhgdfgdfcgd', '2024-10-21 16:47:49'),
(143, 'Rakib', 'Ferxin2', 'gdghfdghfd', '2024-10-21 16:48:14'),
(144, 'Ferxin2', 'Rakib', 'setrsrtsdrtgdrgd', '2024-10-21 16:48:25'),
(145, 'Rakib', 'Ferxin2', 'fuck you', '2024-10-21 16:48:37'),
(146, 'Ferxin2', 'Rakib', 'always', '2024-10-21 16:48:44'),
(147, 'Arka', 'Rakib', 'xzcvzxvxcv', '2024-10-21 16:59:14'),
(148, 'Rakib', 'Arka', 'cxvxcvxcxcvx', '2024-10-21 16:59:18'),
(149, 'Arka', 'Rakib', 'xdfgdfgdfcgdf', '2024-10-21 16:59:21'),
(150, 'Rakib', 'Arka', 'dhdfghfdgchbgfdgchbfg', '2024-10-21 16:59:24'),
(151, 'Arka', 'Rakib', 'ghfhfhfgh', '2024-10-21 18:22:07'),
(152, 'Rakib', 'Arka', 'gfghfhfghfhhfg', '2024-10-21 18:22:11'),
(153, 'Arka', 'Rakib', 'fghjfghfgvhfcvh', '2024-10-21 18:22:15'),
(154, 'Rakib', 'Arka', 'gfhfghfvghfghfgch', '2024-10-21 18:22:18'),
(155, 'Rakib', 'Imroj', 'eryteyteryt', '2024-10-21 18:29:26'),
(156, 'Rakib', 'Arka', 'regerthygrtgyrtegtyr', '2024-10-21 18:30:14'),
(157, 'Arka', 'Rakib', 'etghertghertgerdt', '2024-10-21 18:30:18'),
(158, 'Rakib', 'Arka', 'Hi', '2024-10-21 18:36:54'),
(159, 'Arka', 'Rakib', 'hi', '2024-10-21 18:37:16'),
(160, 'Rakib', 'Arka', 'sgfredgderg', '2024-10-21 18:37:36'),
(161, 'Arka', 'Rakib', 'sdgdsrgdrgrde', '2024-10-21 18:37:38'),
(162, 'Rakib', 'Arka', 'gedrgedr', '2024-10-21 18:37:41'),
(163, 'Arka', 'Rakib', 'gedrgredgre', '2024-10-21 18:37:43'),
(164, 'Arka', 'Imroj', 'vhjmghvjmghbm', '2024-11-02 11:48:57'),
(165, 'Arka', 'Imroj', 'gfdhfghfghfgvcnhbfgvnfgvhn', '2024-11-02 11:49:38'),
(166, 'Imroj', 'Arka', 'dsry5tertygdferghfdt', '2024-11-02 11:51:53'),
(167, 'Sawon', 'Imroj', 'srtedrtgterdg', '2024-11-02 12:02:28'),
(168, 'Sawon', 'Arif', 'dfgtgyjikfghyjdcfg hfctghfvtgc njfgyvtu', '2024-11-02 12:02:38'),
(169, 'Arif', 'Sawon', 'drytgredtyhrft6yhrftgjhftygjutgy', '2024-11-02 12:02:48'),
(170, 'Touhid', 'Imroj', 'hi', '2024-11-19 16:12:35'),
(171, 'Imroj', 'Touhid', 'hi', '2024-11-19 16:12:40');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `name`, `phone`, `email`) VALUES
(5, 'Imroj', '1234', 'Imroj Hasan', '01715153151', 'imroj50@student.sust.edu'),
(10, 'Sawon', '1234', 'Abu sayead Sawon', '01845345', 'sawon @gmail.com'),
(14, 'Arif', '1234', 'Arif Rabbani', '01849831855', 'arifrabbani00000@gmail.com'),
(15, 'Touhid', '1234', 'Touhid rohman', '234r325434', 'touhid@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
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
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=172;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
