INSERT INTO contentTypes(id, name) 
    VALUES(1, 'FOLDER');

INSERT INTO contentTypes(id, name) 
    VALUES(2, 'UNKNOWN');

INSERT INTO contentTypes(id, name) 
    VALUES(3, 'TEXT');

INSERT INTO contentTypes(id, name) 
    VALUES(4, 'HTML');

INSERT INTO contentTypes(id, name) 
    VALUES(5, 'GIF');

INSERT INTO contentTypes(id, name) 
    VALUES(6, 'JPG');

INSERT INTO contentTypes(id, name) 
    VALUES(7, 'PNG');

INSERT INTO contentTypes(id, name) 
    VALUES(8, 'FLASH');

INSERT INTO contentTypes(id, name) 
    VALUES(9, 'PDF');

-- Resources

INSERT INTO contents(name, isFolder, data, createdDate, updatedDate, folderId, contentTypeId) 
    VALUES('ROOT', true, NULL, '2006-3-28 4:34:58.0', '2006-3-28 4:34:58.0', null, 1);

