-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Svetog Save bb', '+381/60-222222', 'Srbija', true, 'Beograd', 'Vilotije', 'vilimali@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true);
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Brace Ribnikara 12', '+381/61-1234567', 'Srbija', true, 'Beograd', 'Pacijent', 'pacijent@gmail.com', 'Prvi', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true);
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', false, 'Novi Sad', 'Milutin', 'test@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true);
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/69-5504205', 'Srbija', false, 'Novi Sad', 'Dragoslav', 'leagueofpro75@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true);


INSERT INTO public.admin_centra(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'admin@admin.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false);

INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Jovana Obrenovica 83A', '+381/61-5534209', 'Crna Gora', 'Pljevlja', 'Sveti vid', 4.0, 'Vrlo lepo');
INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Danila Kisa 5', '+381/61-31234123', 'Srbija', 'Beograd', 'MC', 3.5, 'Sve naj');
INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Bulevar Oslobodjenja', '+381/65-1241231','Srbija' ,'Novi Sad', 'Klinika Centar', 3.0, 'Savrsena');
	
INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, klinika_id, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/61-3453231', 'Srbija', true, 'Novi Sad', 'Damjan', 'damjanbanjac@gmail.com', 'Banjac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, true);
INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, klinika_id, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/62-31231231', 'Srbija', true, 'Novi Sad', 'Damjanko', 'damjanbanjac14@gmail.com', 'Banjac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 2, true);
	
INSERT INTO public.authority(name)VALUES ('PACIJENT');
INSERT INTO public.authority(name)VALUES ('LEKAR');
INSERT INTO public.authority(name)VALUES ('ADMIN');
INSERT INTO public.authority(name)VALUES ('ADMINCENTRA');
INSERT INTO public.authority(name)VALUES ('MEDICINSKASESTRA');


INSERT INTO public.tip_pregleda(
	id, cena, naziv, oznaka, klinika_id)
	VALUES (nextval('tippregleda_id_seq'), '200', 'tip1', ' ozn1', 1);
INSERT INTO public.tip_pregleda(
	id, cena, naziv, oznaka, klinika_id)
	VALUES (nextval('tippregleda_id_seq'), '5000', 'Ocni', ' ocni', 2);

	
INSERT INTO public.sala_klinike(
	id, broj, naziv, klinika_id)
	VALUES (nextval('salaklinike_id_seq'), 201, 'Sok Soba', 1);
INSERT INTO public.sala_klinike(
	id, broj, naziv, klinika_id)
	VALUES (nextval('salaklinike_id_seq'), 100, '100', 2);

INSERT INTO public.lekar(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'lekar@gmail.com', 'Banjac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, '20:00', '12:00', 1, 1, true);
INSERT INTO public.lekar(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, ocena, radno_do, radno_od, klinika_id, tip_pregleda_id, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Janka Veska 12', '+381/65-5504205', 'Srbija', true, 'Beograd', 'Bolo', 'lekar2@gmail.com', 'Banjac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 1, '14:00', '12:00', 2, 2, true);


	
INSERT INTO public.pregledi(
	id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id)
	VALUES (nextval('pregled_id_seq'), 2000, '30/01/2020', null, 1.5, '13:00', false, 8, 1, 1);
INSERT INTO public.pregledi(
	id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id)
	VALUES (nextval('pregled_id_seq'), 5000, '21/12/2019', null, 0.5, '13:00', false, 9, 2, 2);

INSERT INTO public.operacija(
	id, cena, datum, id_pacijenta, trajanje_operacije, vreme, zavrsen, sala_klinike_id, tip_operacije_id)
	VALUES (nextval('operacija_id_seq'), 2000, '12/12/2020', 2, 1.5, '12:00', false, 1, 1);
INSERT INTO public.operacija(
	id, cena, datum, id_pacijenta, trajanje_operacije, vreme, zavrsen, sala_klinike_id, tip_operacije_id)
	VALUES (nextval('operacija_id_seq'), 2000, '01/05/2020', 2, 1, '14:00', true, 1, 1);

	
INSERT INTO public.lekari_operacije(
	operacija_id, lekar_id)
	VALUES (1, 8);
INSERT INTO public.lekari_operacije(
	operacija_id, lekar_id)
	VALUES (2, 8);


INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Brace Ribnikara 12', '+381/61-1234567', 'Srbija', false, 'Beograd', 'Qacijent', 'pacijent2@gmail.com', 'Prvi', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true);
	
	INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra, promenjena_sifra)
	VALUES (nextval('users_id_seq'), 'Brace Ribnikara 12', '+381/61-1234567', 'Srbija', true, 'Beograd', 'Bole', 'pacijent3@gmail.com', 'Prvi', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true);
INSERT INTO public.pregledi(
	id, cena, datum, id_pacijenta, trajanje_pregleda, vreme, zavrsen, lekar_id, sala_klinike_id, tip_pregleda_id)
	VALUES (nextval('pregled_id_seq'), 2000, '31/01/2020', 1, 0.5, '14:00', true, 8, 1, 1);



	
	/*INSERT INTO public.medicinska_sestra(
	id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, promenjena_sifra, sifra, radno_do, radno_od, klinika_id)
	VALUES (nextval('users_id_seq'), 'Janka Veska 12', '+381/65-5504205', 'Srbija', true, 'Beograd', 'sestra', 'damjanbanjac14@gmail.com','Banjac',true, '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','20:00', '12:00', 1);
	
INSERT INTO public.godisnji(
	id, datum_do, datum_od, odobren, lekar_id, sestra_id)
	VALUES (nextval('godisnji_id_seq'), '31/01/2022', '01/01/2022', false, null, 1); */

INSERT INTO public.user_authority(user_id, authority_id)VALUES (1, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (2, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (3, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (4, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (5, 4);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (6, 3);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (7, 3);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (8, 2);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (9, 2);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (10, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (11, 1);
INSERT INTO public.user_authority(user_id, authority_id)VALUES (12, 2);

