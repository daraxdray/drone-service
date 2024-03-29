INSERT INTO `drone` (model,state,weight_limit,battery_capacity,serial_number) VALUES
('Lightweight','IDLE','200','20','AA103399BBer0099kk'),
('Cruiserweight','IDLE','400','40','CC103399BBer0099li'),
('Lightweight','IDLE','190','60','DD103399BBer0099iu'),
('Heavyweight','IDLE','500','40','EE103399HIer0099po'),
('Heavyweight','IDLE','450','70','FF103399BU9r0099ui'),
('Cruiserweight','IDLE','300','70','HH103395GBer0099hy'),
('Middleweight','IDLE','250','90','LL103399BAer0099gh'),
('Cruiserweight','IDLE','300','80','PP103356BBer0099nj'),
('Lightweight','IDLE','120','50','XY103399Uher0099kk'),
('Heavyweight','IDLE','450','40','HU103399BBer0099uj');


INSERT INTO `medication` (code, image, name, weight) VALUES
('JNV','image-url','Januvia','20'),
('FLG','image-url','Acetaminophen','5'),
('ADR','image-url','Adderall','120'),
('AMT','image-url','Amitriptyline','40'),
('AML','image-url','Amlodipine','10'),
('AMX','image-url','Amoxicillin','420'),
('ATV','image-url','Ativan','50'),
('ATVS','image-url','Atorvastatin','220'),
('TT','image-url','TetraAzithromycin','320'),
('AZC','image-url','Azithromycin','290');


INSERT INTO recipient (address, recipient_name ) VALUES
('777 Brockton Avenue' ,'Delyan Lilov'),
('Abington MA 2351. 30 Memorial Drive','Elena Marinova' ),
('Avon MA 2322. 250 Hartford Avenue','Damilola Daramola' ),
('Bellingham MA 2019. 700 Oak Street', 'Elena Dimovska' );


INSERT INTO delivery (delivery_status, delivery_time, drone_id,medication_id,recipient_id)
VALUES (FALSE, '2023-01-23 10:44:12.310543', 2,1,1),
(FALSE, '2023-01-23 10:44:12.310543', 2,2,1);