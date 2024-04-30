CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
                         `role_id` int NOT NULL AUTO_INCREMENT,
                         `role_name` varchar(255) NOT NULL,
                         PRIMARY KEY (`role_id`),
                         UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `permissions` (
                               `permission_id` int NOT NULL AUTO_INCREMENT,
                               `permission_name` varchar(255) NOT NULL,
                               PRIMARY KEY (`permission_id`),
                               UNIQUE KEY `permission_name` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
                              `user_id` int NOT NULL,
                              `role_id` int NOT NULL,
                              PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role_permissions` (
                                    `role_id` int NOT NULL,
                                    `permission_id` int NOT NULL,
                                    PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert users values (null,'tom','1111'),
                    (null,'jack','1234');
select * from users;

insert roles values (null,'admin'),
                    (null,'user');
select * from roles;

insert permissions values (null,'search'),
                          (null,'deepin'),
                          (null,'view'),
                          (null,'modify');
select * from permissions;

insert user_roles(user_id, role_id)  values (1,1),
                                            (2,2);
insert role_permissions(role_id, permission_id) values (1,1),
                                                       (1,2),
                                                       (1,3),
                                                       (1,4),
                                                       (2,3);
select * from role_permissions;