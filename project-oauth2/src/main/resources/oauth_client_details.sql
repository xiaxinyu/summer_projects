/*
-- Query: select * from oauth_client_details
LIMIT 0, 1000

-- Date: 2019-03-26 11:42
*/
INSERT INTO `oauth_client_details` (`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) VALUES ('summer',NULL,'$2a$10$CJrILjwQI5Y5IJUQu4.G2.DbKyYwQhKS75oIHLLeOBJuUYPf.naZa','read,write','password,implicit,client_credentials,authorization_code,refresh_token','http://localhost:8080',NULL,NULL,NULL,NULL,NULL);
