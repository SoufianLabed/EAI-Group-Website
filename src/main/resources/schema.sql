CREATE SEQUENCE roles_id_seq;
CREATE SEQUENCE users_id_seq;

CREATE TABLE public.roles
(
    id integer NOT NULL DEFAULT nextval('roles_id_seq'),
    name character varying(40) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);



CREATE TABLE public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'),
    description character varying(255) ,
    email character varying(255) ,
    password character varying(255),
    username character varying(255),
    country character varying(255) ,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username)
);



CREATE TABLE public.user_roles
(
    user_id bigint NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

insert into roles (id, name)
values (1,'ROLE_USER');
insert into public.roles (id, name)
values (2,'ROLE_COMMUNITY_MANAGER');
insert into public.roles (id, name)
values (3,'ROLE_ADMIN');
insert into public.roles (id, name)
values (4,'ROLE_SUPPLIER');