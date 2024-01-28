create sequence IF NOT EXISTS hibernate_sequence start with 1 increment by 1;
create TABLE IF NOT EXISTS app (
name VARCHAR(255) NOT NULL,
uri VARCHAR(512) NOT NULL UNIQUE,
CONSTRAINT pk_app PRIMARY KEY (uri)
);

create TABLE IF NOT EXISTS hit(
id_hit bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
hit_uri VARCHAR(512),
time_hit timestamptz NOT NULL,
ip_user VARCHAR(255) NOT NULL,
CONSTRAINT pk_hit PRIMARY KEY (id_hit)
);

ALTER TABLE hit DROP CONSTRAINT IF EXISTS FK_hit;
ALTER TABLE hit ADD CONSTRAINT FK_hit FOREIGN KEY (hit_uri)  REFERENCES app (uri);