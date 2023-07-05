create database if not exists `rangiffler_geo`;

USE `rangiffler_geo`;

create table if not exists countries
(
    id   BINARY(16) unique  not null default (UUID_TO_BIN(UUID(), TRUE)),
    code varchar(50) unique not null,
    name varchar(50) unique not null,
    primary key (id)
);

delete
from countries
where code is not null;

insert into countries(code, name)
values ('FJ', 'Fiji');
insert into countries(code, name)
values ('TZ', 'Tanzania');
insert into countries(code, name)
values ('EH', 'Western Sahara');
insert into countries(code, name)
values ('CA', 'Canada');
insert into countries(code, name)
values ('US', 'United States');
insert into countries(code, name)
values ('KZ', 'Kazakhstan');
insert into countries(code, name)
values ('UZ', 'Uzbekistan');
insert into countries(code, name)
values ('PG', 'Papua New Guinea');
insert into countries(code, name)
values ('ID', 'Indonesia');
insert into countries(code, name)
values ('AR', 'Argentina');
insert into countries(code, name)
values ('CL', 'Chile');
insert into countries(code, name)
values ('CD', 'Democratic Republic of the Congo');
insert into countries(code, name)
values ('SO', 'Somalia');
insert into countries(code, name)
values ('KE', 'Kenya');
insert into countries(code, name)
values ('SD', 'Sudan');
insert into countries(code, name)
values ('TD', 'Chad');
insert into countries(code, name)
values ('HT', 'Haiti');
insert into countries(code, name)
values ('DO', 'Dominican Republic');
insert into countries(code, name)
values ('RU', 'Russia');
insert into countries(code, name)
values ('BS', 'Bahamas');
insert into countries(code, name)
values ('FK', 'Falkland Islands');
insert into countries(code, name)
values ('NO', 'Norway');
insert into countries(code, name)
values ('GL', 'Greenland');
insert into countries(code, name)
values ('TL', 'Timor-Leste');
insert into countries(code, name)
values ('ZA', 'South Africa');
insert into countries(code, name)
values ('LS', 'Lesotho');
insert into countries(code, name)
values ('MX', 'Mexico');
insert into countries(code, name)
values ('UY', 'Uruguay');
insert into countries(code, name)
values ('BR', 'Brazil');
insert into countries(code, name)
values ('BO', 'Bolivia');
insert into countries(code, name)
values ('PE', 'Peru');
insert into countries(code, name)
values ('CO', 'Colombia');
insert into countries(code, name)
values ('PA', 'Panama');
insert into countries(code, name)
values ('CR', 'Costa Rica');
insert into countries(code, name)
values ('NI', 'Nicaragua');
insert into countries(code, name)
values ('HN', 'Honduras');
insert into countries(code, name)
values ('SV', 'El Salvador');
insert into countries(code, name)
values ('GT', 'Guatemala');
insert into countries(code, name)
values ('BZ', 'Belize');
insert into countries(code, name)
values ('VE', 'Venezuela');
insert into countries(code, name)
values ('GY', 'Guyana');
insert into countries(code, name)
values ('SR', 'Suriname');
insert into countries(code, name)
values ('FR', 'France');
insert into countries(code, name)
values ('EC', 'Ecuador');
insert into countries(code, name)
values ('PR', 'Puerto Rico');
insert into countries(code, name)
values ('JM', 'Jamaica');
insert into countries(code, name)
values ('CU', 'Cuba');
insert into countries(code, name)
values ('ZW', 'Zimbabwe');
insert into countries(code, name)
values ('BW', 'Botswana');
insert into countries(code, name)
values ('NA', 'Namibia');
insert into countries(code, name)
values ('SN', 'Senegal');
insert into countries(code, name)
values ('ML', 'Mali');
insert into countries(code, name)
values ('MR', 'Mauritania');
insert into countries(code, name)
values ('BJ', 'Benin');
insert into countries(code, name)
values ('NE', 'Niger');
insert into countries(code, name)
values ('NG', 'Nigeria');
insert into countries(code, name)
values ('CM', 'Cameroon');
insert into countries(code, name)
values ('TG', 'Togo');
insert into countries(code, name)
values ('GH', 'Ghana');
insert into countries(code, name)
values ('CI', 'Côted Ivoire');
insert into countries(code, name)
values ('GN', 'Guinea');
insert into countries(code, name)
values ('GW', 'Guinea-Bissau');
insert into countries(code, name)
values ('LR', 'Liberia');
insert into countries(code, name)
values ('SL', 'Sierra Leone');
insert into countries(code, name)
values ('BF', 'Burkina Faso');
insert into countries(code, name)
values ('CF', 'Central African Republic');
insert into countries(code, name)
values ('CG', 'Republic of the Congo');
insert into countries(code, name)
values ('GA', 'Gabon');
insert into countries(code, name)
values ('GQ', 'Equatorial Guinea');
insert into countries(code, name)
values ('ZM', 'Zambia');
insert into countries(code, name)
values ('MW', 'Malawi');
insert into countries(code, name)
values ('MZ', 'Mozambique');
insert into countries(code, name)
values ('SZ', 'Eswatini');
insert into countries(code, name)
values ('AO', 'Angola');
insert into countries(code, name)
values ('BI', 'Burundi');
insert into countries(code, name)
values ('IL', 'Israel');
insert into countries(code, name)
values ('LB', 'Lebanon');
insert into countries(code, name)
values ('MG', 'Madagascar');
insert into countries(code, name)
values ('PS', 'Palestine');
insert into countries(code, name)
values ('GM', 'The Gambia');
insert into countries(code, name)
values ('TN', 'Tunisia');
insert into countries(code, name)
values ('DZ', 'Algeria');
insert into countries(code, name)
values ('JO', 'Jordan');
insert into countries(code, name)
values ('AE', 'United Arab Emirates');
insert into countries(code, name)
values ('QA', 'Qatar');
insert into countries(code, name)
values ('KW', 'Kuwait');
insert into countries(code, name)
values ('IQ', 'Iraq');
insert into countries(code, name)
values ('OM', 'Oman');
insert into countries(code, name)
values ('VU', 'Vanuatu');
insert into countries(code, name)
values ('KH', 'Cambodia');
insert into countries(code, name)
values ('TH', 'Thailand');
insert into countries(code, name)
values ('LA', 'Lao PDR');
insert into countries(code, name)
values ('MM', 'Myanmar');
insert into countries(code, name)
values ('VN', 'Vietnam');
insert into countries(code, name)
values ('KP', 'Dem. Rep. Korea');
insert into countries(code, name)
values ('KR', 'Republic of Korea');
insert into countries(code, name)
values ('MN', 'Mongolia');
insert into countries(code, name)
values ('IN', 'India');
insert into countries(code, name)
values ('BD', 'Bangladesh');
insert into countries(code, name)
values ('BT', 'Bhutan');
insert into countries(code, name)
values ('NP', 'Nepal');
insert into countries(code, name)
values ('PK', 'Pakistan');
insert into countries(code, name)
values ('AF', 'Afghanistan');
insert into countries(code, name)
values ('TJ', 'Tajikistan');
insert into countries(code, name)
values ('KG', 'Kyrgyzstan');
insert into countries(code, name)
values ('TM', 'Turkmenistan');
insert into countries(code, name)
values ('IR', 'Iran');
insert into countries(code, name)
values ('SY', 'Syria');
insert into countries(code, name)
values ('AM', 'Armenia');
insert into countries(code, name)
values ('SE', 'Sweden');
insert into countries(code, name)
values ('BY', 'Belarus');
insert into countries(code, name)
values ('UA', 'Ukraine');
insert into countries(code, name)
values ('PL', 'Poland');
insert into countries(code, name)
values ('AT', 'Austria');
insert into countries(code, name)
values ('HU', 'Hungary');
insert into countries(code, name)
values ('MD', 'Moldova');
insert into countries(code, name)
values ('RO', 'Romania');
insert into countries(code, name)
values ('LT', 'Lithuania');
insert into countries(code, name)
values ('LV', 'Latvia');
insert into countries(code, name)
values ('EE', 'Estonia');
insert into countries(code, name)
values ('DE', 'Germany');
insert into countries(code, name)
values ('BG', 'Bulgaria');
insert into countries(code, name)
values ('GR', 'Greece');
insert into countries(code, name)
values ('TR', 'Turkey');
insert into countries(code, name)
values ('AL', 'Albania');
insert into countries(code, name)
values ('HR', 'Croatia');
insert into countries(code, name)
values ('CH', 'Switzerland');
insert into countries(code, name)
values ('LU', 'Luxembourg');
insert into countries(code, name)
values ('BE', 'Belgium');
insert into countries(code, name)
values ('NL', 'Netherlands');
insert into countries(code, name)
values ('PT', 'Portugal');
insert into countries(code, name)
values ('ES', 'Spain');
insert into countries(code, name)
values ('IE', 'Ireland');
insert into countries(code, name)
values ('NC', 'New Caledonia');
insert into countries(code, name)
values ('SB', 'Solomon Islands');
insert into countries(code, name)
values ('NZ', 'New Zealand');
insert into countries(code, name)
values ('AU', 'Australia');
insert into countries(code, name)
values ('LK', 'Sri Lanka');
insert into countries(code, name)
values ('CN', 'China');
insert into countries(code, name)
values ('TW', 'Taiwan');
insert into countries(code, name)
values ('IT', 'Italy');
insert into countries(code, name)
values ('DK', 'Denmark');
insert into countries(code, name)
values ('GB', 'United Kingdom');
insert into countries(code, name)
values ('IS', 'Iceland');
insert into countries(code, name)
values ('AZ', 'Azerbaijan');
insert into countries(code, name)
values ('GE', 'Georgia');
insert into countries(code, name)
values ('PH', 'Philippines');
insert into countries(code, name)
values ('MY', 'Malaysia');
insert into countries(code, name)
values ('BN', 'Brunei Darussalam');
insert into countries(code, name)
values ('SI', 'Slovenia');
insert into countries(code, name)
values ('FI', 'Finland');
insert into countries(code, name)
values ('SK', 'Slovakia');
insert into countries(code, name)
values ('CZ', 'Czech Republic');
insert into countries(code, name)
values ('ER', 'Eritrea');
insert into countries(code, name)
values ('JP', 'Japan');
insert into countries(code, name)
values ('PY', 'Paraguay');
insert into countries(code, name)
values ('YE', 'Yemen');
insert into countries(code, name)
values ('SA', 'Saudi Arabia');
insert into countries(code, name)
values ('CYP', 'Northern Cyprus');
insert into countries(code, name)
values ('CY', 'Cyprus');
insert into countries(code, name)
values ('MA', 'Morocco');
insert into countries(code, name)
values ('EG', 'Egypt');
insert into countries(code, name)
values ('LY', 'Libya');
insert into countries(code, name)
values ('ET', 'Ethiopia');
insert into countries(code, name)
values ('DJ', 'Djibouti');
insert into countries(code, name)
values ('SOM', 'Somaliland');
insert into countries(code, name)
values ('UG', 'Uganda');
insert into countries(code, name)
values ('RW', 'Rwanda');
insert into countries(code, name)
values ('BA', 'Bosnia and Herzegovina');
insert into countries(code, name)
values ('MK', 'Macedonia');
insert into countries(code, name)
values ('RS', 'Serbia');
insert into countries(code, name)
values ('ME', 'Montenegro');
insert into countries(code, name)
values ('XK', 'Kosovo');
insert into countries(code, name)
values ('TT', 'Trinidad and Tobago');
insert into countries(code, name)
values ('SS', 'South Sudan');
