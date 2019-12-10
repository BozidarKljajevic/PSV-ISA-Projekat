-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za oba user-a je 123
INSERT INTO public.pacijent(
	 id, adresa, broj_telefona, drzava, enabled, grad, ime, mail, prezime, sifra)
	VALUES (nextval('users_id_seq'), 'Danila Kisa 5', '+381/65-5504205', 'Srbija', true, 'Novi Sad', 'Damjan', 'panticdamjan@gmail.com', 'Pantic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra');


INSERT INTO public.authority(name)VALUES ('PACIJENT');
INSERT INTO public.authority(name)VALUES ('LEKAR');
INSERT INTO public.authority(name)VALUES ('ADMIN');

INSERT INTO public.user_authority(user_id, authority_id)VALUES (1, 1);