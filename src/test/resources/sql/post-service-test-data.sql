INSERT INTO `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
VALUES (1, 'test@test.com', 'sjna', 'Seoul', 'aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaaa', 'ACTIVE', 0);

INSERT INTO `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
VALUES (2, 'test2@test.com', 'sjna2', 'Seoul', 'aaaaaaaaaaaaa-aaaaaaaa-aaaaa-aaaaaab', 'PENDING', 0);

INSERT INTO `posts` (`id`, `content`, `created_at`, `modified_at`, `user_id`)
VALUES (1, 'hello-world', 1678530673958, 1678530673958, 1);