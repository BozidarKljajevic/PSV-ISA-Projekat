INSERT INTO public.pacijent(
	id, adresa, aktivan, broj_telefona, drzava, grad, ime, mail, prezime, sifra)
	VALUES (nextval('pacijent_id_seq'), 'Danila Kisa 5', true, '+381/65-5504205', 'Srbija', 'Novi Sad', 'Damjan', 'panticdamjan@fmail.com', 'Pantic', '1234');