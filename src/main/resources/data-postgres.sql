-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'panticdamjan@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');


INSERT INTO public.authority(name)VALUES ('PACIJENT');
INSERT INTO public.authority(name)VALUES ('LEKAR');
INSERT INTO public.authority(name)VALUES ('ADMIN');

INSERT INTO public.user_authority(user_id, authority_id)VALUES (1, 1);

INSERT INTO public.klinika(
	id, adresa, broj_telefona, drzava, grad, naziv, ocena, opis)
	VALUES (nextval('klinika_id_seq'), 'Jovana Obrenovica 83A', '+381/61-5534209', 'Crna Gora', 'Pljevlja', 'Sveti vid', 3.5, 'Vrlo lepo');




INSERT INTO public.admin_klinike(
	id, adresa, broj_telefona, drzava, grad, ime, lozinka, mail, prezime,klinika_id)
	VALUES (nextval('admin_klinike_id_seq'),'Janka Veselinovica 17', '+381/64-7554608', 'Srbija', 'Novi Sad', 'Damjan', 'damjanbanjac', 'damjanbanjac14@gmail.com', 'Banjac',1);

INSERT INTO public.admin_centra(
	mail, lozinka)
	VALUES ('admin@admin.com', 'admin');
	

INSERT INTO public.neaktivan_pacijent(
	id, adresa, broj_telefona, drzava, grad, ime, mail, prezime, sifra)
	VALUES (nextval('neaktivan_pacijent_id_seq'), 'Janka Čmelnika 5', '+381/65-5504964', 'Srbija', 'Prijepolje', 'Milivoje', 'panticmilivoje@gmail.com', 'Pantic', 'xyz');
INSERT INTO public.neaktivan_pacijent(
	id, adresa, broj_telefona, drzava, grad, ime, mail, prezime, sifra)
	VALUES (nextval('neaktivan_pacijent_id_seq'), 'Janka Čmelnika 5', '+381/65-5504964', 'Srbija', 'Prijepolje', 'Milivoje2', 'panticmilivoje2@gmail.com', 'Pantic', 'xyz');	

INSERT INTO public.medicinsko_osoblje(
	id, adresa, broj_telefona, drzava, grad, ime, lekar, lozinka, mail, prezime, radno_do, radno_od)
	VALUES (nextval('medicinsko_osoblje_id_seq'), 'Koste Racina 15', '+381/64-7554699', 'Srbija', 'Beograd', 'John', true, 'johnisner', 'damjanbanjac@gmail.com', 'Isner', '20:00', '10:00');

INSERT INTO public.bolesti(
	naziv, sifra)
	VALUES ('kijavica', '552');
INSERT INTO public.bolesti(
	naziv, sifra)
	VALUES ('sida', '59');

INSERT INTO public.lek(
	naziv, sifra)
	VALUES ('pantenol', '12');
INSERT INTO public.lek(
	naziv, sifra)
	VALUES ('strepsils', '52');
