INSERT INTO public.users (id, enabled, id_group, password, role, username)
VALUES (1, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_ADMIN', 'admin@mail.com');

INSERT INTO public.users (id, enabled, id_group, password, role, username)
VALUES (2, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_MEMBER', 'molly@weasley.com');

INSERT INTO public.users (id, enabled, id_group, password, role, username)
VALUES (3, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_PARENT', 'artur@weasley.com');

-- SELECT setval('hibernate_sequence', 100, true);