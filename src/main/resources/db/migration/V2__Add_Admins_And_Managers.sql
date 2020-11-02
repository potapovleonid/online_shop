INSERT INTO users_tbl (user_id, archive_fld, email_fld, name_fld, password_fld, role_fld, bucket_bucket_id)
values (1, false, 'admin@mail.ru', 'admin', '$2a$10$dGyor0A.eypk24kZBG93z.EpqJIA6PG5WwNA4xvAHXK5wUdAZcaVm', 'ADMIN', null);
INSERT INTO users_tbl (user_id, archive_fld, email_fld, name_fld, password_fld, role_fld, bucket_bucket_id)
values (2, false, 'manager@mail.ru', 'manager', '$2a$10$RqTX4x9amB2NwWwQMabca.5U9gIusgRjSyIwzxR.agt8O3Kz6Npl2', 'MANAGER', null);
INSERT INTO users_tbl (user_id, archive_fld, email_fld, name_fld, password_fld, role_fld, bucket_bucket_id)
values (3, false, 'super@mail.ru', 'superman', '$2a$10$6ryLu6cwRWJE4o/HtmQkuOqYFW/rJveN9nD4EPuMyHwdoQXe8ME2y', 'SUPER_ADMIN', null);

ALTER SEQUENCE user_seq RESTART WITH 4;
