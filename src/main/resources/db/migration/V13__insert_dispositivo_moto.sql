UPDATE moto 
SET dispositivo_id = (SELECT id FROM dispositivo WHERE moto_id = 1)
WHERE id = 1;