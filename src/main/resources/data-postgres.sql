INSERT INTO public.users (id, enabled, id_group, password, role, username)
VALUES (1, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_ADMIN', 'admin@parcom.com');

INSERT INTO public.users (id, enabled, id_group, password, role, username)
VALUES (2, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_MEMBER', 'mom@parcom.com');

INSERT INTO public.users (id, enabled, id_group, password, role, username)
VALUES (3, true, 21, '$2a$10$kb27tY1oqDxSBzx9xEhRe.F70uX2cAm4qvphS1Gf0ppSNtOlho4EK', 'ROLE_PARENT', 'dad@parcom.com');

SELECT setval('hibernate_sequence', 100, true);