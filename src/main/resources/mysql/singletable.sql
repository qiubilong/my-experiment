CREATE TABLE `single_table` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `key1` varchar(100) DEFAULT NULL,
                                `key2` int(11) DEFAULT NULL,
                                `key3` varchar(100) DEFAULT NULL,
                                `key_part1` varchar(100) DEFAULT NULL,
                                `key_part2` varchar(100) DEFAULT NULL,
                                `key_part3` varchar(100) DEFAULT NULL,
                                `common_field` varchar(100) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uk_key2` (`key2`),
                                KEY `idx_key1` (`key1`),
                                KEY `idx_key3` (`key3`),
                                KEY `idx_key_part` (`key_part1`,`key_part2`,`key_part3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
