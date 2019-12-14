INSERT INTO public.users (id, enabled, id_group, password, role, username,id_student)
VALUES (1, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_ADMIN', 'admin@mail.com',31);

INSERT INTO public.users (id, enabled, id_group, password, role, username,id_student)
VALUES (2, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_MEMBER', 'molly@weasley.com',32);

INSERT INTO public.users (id, enabled, id_group, password, role, username,id_student)
VALUES (3, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_PARENT', 'artur@weasley.com',32);

-- SELECT setval('hibernate_sequence', 100, true);