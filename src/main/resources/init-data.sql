-- 创建测试表
CREATE TABLE IF NOT EXISTS `test` (
  `question_id` INT AUTO_INCREMENT PRIMARY KEY,
  `content` TEXT NOT NULL,
  `correct_ans` VARCHAR(10) NOT NULL,
  `chapter` VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建讨论区表
CREATE TABLE IF NOT EXISTS `discussion` (
  `post_id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(100) NOT NULL,
  `content` TEXT NOT NULL,
  `author_id` INT NOT NULL,
  `type` ENUM('课程讨论', '实验答疑') NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`author_id`) REFERENCES `user`(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
INSERT INTO `test` (`content`, `correct_ans`, `chapter`) VALUES
(
  '{"question": "TCP三次握手发生在哪一层？", "options": ["A. 应用层", "B. 传输层", "C. 网络层", "D. 数据链路层"]}',
  'B',
  '传输层'
),
(
  '{"question": "HTTP协议的默认端口是？", "options": ["A. 80", "B. 443", "C. 22", "D. 3306"]}',
  'A',
  '应用层'
),
(
  '{"question": "IPv6的地址长度是多少位？", "options": ["A. 32", "B. 64", "C. 128", "D. 256"]}',
  'C',
  '网络层'
),
(
  '{"question": "DNS解析的作用是？", "options": ["A. 加密数据", "B. 将域名转换为IP地址", "C. 管理网络设备", "D. 分配IP地址"]}',
  'B',
  '应用层'
),
(
  '{"question": "OSI模型中，数据链路层的功能是？", "options": ["A. 路由选择", "B. 数据帧传输", "C. 端到端连接", "D. 数据压缩"]}',
  'B',
  '数据链路层'
),
(
  '{"question": "以下哪项是私有IP地址？", "options": ["A. 8.8.8.8", "B. 192.168.1.1", "C. 172.16.255.255", "D. 203.0.113.1"]}',
  'B',
  '网络层'
),
(
  '{"question": "HTTPS协议使用的加密协议是？", "options": ["A. HTTP", "B. FTP", "C. SSL/TLS", "D. TCP"]}',
  'C',
  '应用层'
),
(
  '{"question": "ARP协议的作用是？", "options": ["A. 将IP地址转换为MAC地址", "B. 管理路由表", "C. 分配IP地址", "D. 加密数据"]}',
  'A',
  '网络层'
),
(
  '{"question": "以下哪个设备工作在网络层？", "options": ["A. 集线器", "B. 交换机", "C. 路由器", "D. 网桥"]}',
  'C',
  '网络层'
),
(
  '{"question": "UDP协议的特点不包括？", "options": ["A. 无连接", "B. 可靠传输", "C. 速度快", "D. 不保证顺序"]}',
  'B',
  '传输层'
),
(
  '{"question": "子网掩码255.255.255.0对应的CIDR表示是？", "options": ["A. /24", "B. /16", "C. /32", "D. /8"]}',
  'A',
  '网络层'
),
(
  '{"question": "FTP协议使用的端口是？", "options": ["A. 20和21", "B. 22", "C. 80", "D. 443"]}',
  'A',
  '应用层'
),
(
  '{"question": "以下哪个是动态路由协议？", "options": ["A. OSPF", "B. ARP", "C. ICMP", "D. DNS"]}',
  'A',
  '网络层'
),
(
  '{"question": "VPN的主要功能是？", "options": ["A. 提高网速", "B. 建立安全隧道", "C. 过滤广告", "D. 管理设备"]}',
  'B',
  '安全技术'
),
(
  '{"question": "IPv4地址的总数量约为？", "options": ["A. 1亿", "B. 43亿", "C. 128亿", "D. 340涧"]}',
  'B',
  '网络层'
);

-- 插入讨论区数据
INSERT INTO `discussion` (`title`, `content`, `author_id`, `type`) VALUES
('TCP三次握手疑问', '请问第三次握手是否可以携带数据？', 1, '课程讨论'),
('实验一配置问题', '交换机VLAN划分后无法通信，求指导！', 2, '实验答疑'),
('HTTP与HTTPS区别', '谁能简单总结下两者的核心差异？', 3, '课程讨论'),
('IP地址冲突', '实验室电脑IP冲突频繁，如何解决？', 1, '实验答疑'),
('OSI模型记忆技巧', '分享一个七层模型的速记口诀~', 2, '课程讨论'),
('路由器配置失败', '静态路由配置后仍无法访问外网', 3, '实验答疑'),
('DNS解析慢', '校园网内DNS解析延迟高，有何优化方法？', 1, '课程讨论'),
('ARP欺骗实验', '求推荐ARP欺骗实验的详细步骤', 2, '实验答疑'),
('IPv6迁移经验', '公司计划迁移到IPv6，需要注意哪些问题？', 3, '课程讨论'),
('VPN连接不稳定', '使用OpenVPN经常断开，如何排查？', 1, '实验答疑'); 