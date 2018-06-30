-- Dumping structure for table ccio.API
CREATE TABLE IF NOT EXISTS `API` (
  `ke` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `ip` tinytext,
  `code` varchar(100) DEFAULT NULL,
  `details` text,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table ccio.Events
CREATE TABLE IF NOT EXISTS `Events` (
  `ke` varchar(50) DEFAULT NULL,
  `mid` varchar(50) DEFAULT NULL,
  `details` text,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.
-- Dumping structure for table ccio.Logs
CREATE TABLE IF NOT EXISTS `Logs` (
  `ke` varchar(50) DEFAULT NULL,
  `mid` varchar(50) DEFAULT NULL,
  `info` text,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table ccio.Monitors
CREATE TABLE IF NOT EXISTS `Monitors` (
  `mid` varchar(50) DEFAULT NULL,
  `ke` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `shto` text,
  `shfr` text,
  `details` longtext,
  `type` varchar(50) DEFAULT 'jpeg',
  `ext` varchar(50) DEFAULT 'webm',
  `protocol` varchar(50) DEFAULT 'http',
  `host` varchar(100) DEFAULT '0.0.0.0',
  `path` varchar(100) DEFAULT '/',
  `port` int(8) DEFAULT '80',
  `fps` int(8) DEFAULT '1',
  `mode` varchar(15) DEFAULT NULL,
  `width` int(11) DEFAULT '640',
  `height` int(11) DEFAULT '360'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table ccio.Presets
CREATE TABLE IF NOT EXISTS `Presets` (
  `ke` varchar(50) DEFAULT NULL,
  `name` text,
  `details` text,
  `type` enum('monitor','event','user') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table ccio.Users
CREATE TABLE IF NOT EXISTS `Users` (
  `ke` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  `auth` varchar(50) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `pass` varchar(100) DEFAULT NULL,
  `details` longtext,
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table ccio.Videos
CREATE TABLE IF NOT EXISTS `Videos` (
  `mid` varchar(50) DEFAULT NULL,
  `ke` varchar(50) DEFAULT NULL,
  `ext` enum('webm','mp4') DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `duration` float DEFAULT NULL,
  `size` float DEFAULT NULL,
  `frames` int(11) DEFAULT NULL,
  `end` timestamp NULL DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '0:Building,1:Complete,2:Read,3:Archive',
  `details` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;