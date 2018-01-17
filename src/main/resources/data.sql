INSERT INTO subject VALUES('9b5a5a2c-8a83-4517-b069-107b6846b5ed', sysdate(), 'Peter Griffin', 1, null);
INSERT INTO subject VALUES('1971eed2-cf7a-4a9d-9506-baf67a4891f4', sysdate(), 'Dyadya Dima', 1, null);
INSERT INTO subject VALUES('416362db-74a4-439e-855e-ad03f9892abf', sysdate(), 'Bob Brown', 1, null);

INSERT INTO subject$credentials VALUES('676a2361-48e9-4825-b83a-43608de3b2e2', 'Peter', 'Griffin');
INSERT INTO subject VALUES('f56f3631-3010-42ae-b2bc-d70390200c36', sysdate(), null, 2, '676a2361-48e9-4825-b83a-43608de3b2e2');

INSERT INTO post VALUES('416262db-74a4-439e-855e-ad03f9892abf', 'Giant traffic jams.', 'f56f3631-3010-42ae-b2bc-d70390200c36');
INSERT INTO post VALUES('412262db-74a4-439e-855e-ad03f9892abf', 'Okey traffic jams.', 'f56f3631-3010-42ae-b2bc-d70390200c36');