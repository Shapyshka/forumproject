-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Сен 28 2021 г., 15:39
-- Версия сервера: 5.7.33
-- Версия PHP: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `restik`
--

-- --------------------------------------------------------

--
-- Структура таблицы `auto`
--

CREATE TABLE `auto` (
  `id` bigint(20) NOT NULL,
  `bludo` varchar(255) DEFAULT NULL,
  `carnum` varchar(10) DEFAULT NULL,
  `oper` varchar(255) DEFAULT NULL,
  `summa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `auto`
--

INSERT INTO `auto` (`id`, `bludo`, `carnum`, `oper`, `summa`) VALUES
(10, 'Яичница', '111', 'Иванов', 111),
(19, 'Чилибэбрики', '1221', 'Иванов', 121212);

-- --------------------------------------------------------

--
-- Структура таблицы `cont`
--

CREATE TABLE `cont` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `prod` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `cont`
--

INSERT INTO `cont` (`id`, `name`, `price`, `prod`) VALUES
(4, 'Договор о поставке помидоров', 11111, 'Помидор'),
(20, 'Договор номер 2223332', 100000, 'Кола');

-- --------------------------------------------------------

--
-- Структура таблицы `del`
--

CREATE TABLE `del` (
  `id` bigint(20) NOT NULL,
  `adress` varchar(100) DEFAULT NULL,
  `bludo` varchar(255) DEFAULT NULL,
  `deliv` varchar(255) DEFAULT NULL,
  `fonum` varchar(40) DEFAULT NULL,
  `summa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `del`
--

INSERT INTO `del` (`id`, `adress`, `bludo`, `deliv`, `fonum`, `summa`) VALUES
(9, '111', 'Яичница', 'Иванов', '111', 111),
(18, '1111111', 'Шпроты', 'Иванов', '1111111', 1111111),
(26, 'ААААААААААААААААА', 'Капуста с капустой (салат)', 'Иванов', '9745', 98);

-- --------------------------------------------------------

--
-- Структура таблицы `emp`
--

CREATE TABLE `emp` (
  `id` bigint(20) NOT NULL,
  `job` varchar(40) DEFAULT NULL,
  `middle_name` varchar(40) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `salary` int(11) NOT NULL,
  `second_name` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `emp`
--

INSERT INTO `emp` (`id`, `job`, `middle_name`, `name`, `salary`, `second_name`) VALUES
(7, 'Копатель', 'Иванович', 'Иван', 222222, 'Иванов'),
(14, 'кто то', 'Кирилович', 'Кирилл', 1111111, 'Кириллов');

-- --------------------------------------------------------

--
-- Структура таблицы `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(27);

-- --------------------------------------------------------

--
-- Структура таблицы `menu`
--

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL,
  `descr` varchar(500) DEFAULT NULL,
  `massa` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `menu`
--

INSERT INTO `menu` (`id`, `descr`, `massa`, `name`, `price`) VALUES
(5, '111', 1111, 'Яичница', 111),
(15, '2323233авапапапа', 333, 'Шпроты', 222),
(16, '1111111111111111111111111111111111111111111111111111111', 223223, 'Чилибэбрики', 2323223),
(24, 'Салат с капустой', 9, 'Капуста с капустой (салат)', 9007);

-- --------------------------------------------------------

--
-- Структура таблицы `stoli`
--

CREATE TABLE `stoli` (
  `id` bigint(20) NOT NULL,
  `number` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `stoli`
--

INSERT INTO `stoli` (`id`, `number`, `size`, `status`) VALUES
(6, 1, 4, b'1'),
(12, 2, 2, b'0');

-- --------------------------------------------------------

--
-- Структура таблицы `store`
--

CREATE TABLE `store` (
  `id` bigint(20) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `store`
--

INSERT INTO `store` (`id`, `name`, `quantity`) VALUES
(1, 'Помидор', 111),
(2, 'Кола', 111),
(3, 'Яйца', 21212),
(22, 'Капуста', 90);

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `active`, `password`, `username`) VALUES
(11, b'1', '$2a$08$pYb9EVpaf8rXmDpIPBZJXuueaqICOKyRhXlRQQGIV/N.VEfLmHsFu', 'abobus'),
(21, b'1', '$2a$08$CrsgdQj/e4ynMpDXgNBGe.rr9I9zs.xq48VwlnnQXtC.kX9whBbKW', '5');

-- --------------------------------------------------------

--
-- Структура таблицы `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user_role`
--

INSERT INTO `user_role` (`user_id`, `roles`) VALUES
(11, 'USER'),
(21, 'USER');

-- --------------------------------------------------------

--
-- Структура таблицы `zakaz`
--

CREATE TABLE `zakaz` (
  `id` bigint(20) NOT NULL,
  `bludo` varchar(255) DEFAULT NULL,
  `ofic` varchar(255) DEFAULT NULL,
  `stol` int(11) DEFAULT NULL,
  `summa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `zakaz`
--

INSERT INTO `zakaz` (`id`, `bludo`, `ofic`, `stol`, `summa`) VALUES
(8, 'Яичница', 'Иванов', 1, 11111),
(17, 'Чилибэбрики', 'Кириллов', 3, 111),
(25, 'Капуста с капустой (салат)', 'Кириллов', 3, 5000);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `auto`
--
ALTER TABLE `auto`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `cont`
--
ALTER TABLE `cont`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `del`
--
ALTER TABLE `del`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `emp`
--
ALTER TABLE `emp`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `stoli`
--
ALTER TABLE `stoli`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`);

--
-- Индексы таблицы `zakaz`
--
ALTER TABLE `zakaz`
  ADD PRIMARY KEY (`id`);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
