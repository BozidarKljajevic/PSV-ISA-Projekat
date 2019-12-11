-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Vilotije', 'vilimali@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');

INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'panticdamjan@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', false, 'Novi Sad', 'Damjan', 'gagimali@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', false, 'Novi Sad', 'Damjan', 'zutimali@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');


	
INSERT INTO public.admin_centra(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'admin@admin.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');

INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Jovana Obrenovica 83A', '+381/61-5534209', 'Crna Gora', 'Pljevlja', 'Sveti vid', 3.5, 'Vrlo lepo');
INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Danila Kisa 5', '+381/61-1111', 'Srbbija', 'Beograd', 'MC', 4.0, 'Sve naj');
	
INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, klinika_id)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'damjanbanjac@gmail.com', 'Banjac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1);

INSERT INTO public.authority(name)VALUES ('PACIJENT');
INSERT INTO public.authority(name)VALUES ('LEKAR');
INSERT INTO public.authority(name)VALUES ('ADMIN');
INSERT INTO public.authority(name)VALUES ('ADMINCENTRA');


INSERT INTO public.tip_pregleda(
	id, cena, naziv, oznaka, klinika_id)
	VALUES (nextval('tippregleda_id_seq'), '200', 'tip1', ' ozn1', 1);
	
INSERT INTO public.lekar(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'lekar@gmail.com', 'Banjac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, '20:00', '08:00', 1, 1);

INSERT INTO public.user_authority(user_id, authority_id)VALUES (1, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (2, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (3, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (4, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (5, 4);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (6, 3);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (7, 2);
