create_namespace 'health'
create 'health:people', 'f1','pic', {NUMREGIONS => 15, SPLITALGO => 'HexStringSplit'}

# put the admin user in
put 'health:people', '1', 'f1:id', '1'
put 'health:people', '1', 'f1:name', 'David Levy'
put 'health:people', '1', 'f1:email', 'dlevy@cloudera.com'
put 'health:people', '1', 'f1:dateOfBirth','1900-01-01'
put 'health:people', '1', 'f1:gender','MALE'
put 'health:people', '1', 'f1:admin','true'

create 'health:hospital', 'f1','pic'
create 'health:hospital_floor', 'f1'
create 'health:hospital_floor_room', 'f1'
create 'health:hospital_floor_room_bed', 'f1'

# Create a hospital
put 'health:hospital', 'aaa', 'f1:id', 'aaa'
put 'health:hospital', 'aaa', 'f1:name', 'Antarctic Childrens Hospital'
put 'health:hospital', 'aaa', 'f1:address1', '1 south pole'


put 'health:hospital_floor', 'aaa_0', 'f1:id', '0'
put 'health:hospital_floor', 'aaa_0', 'f1:name', 'Ground'
put 'health:hospital_floor', 'aaa_0', 'f1:hospitalId', 'aaa'
put 'health:hospital_floor', 'aaa_0', 'f1:corners', '[{x=1317,y=39},{x=1431,y=493},{x=831,y=652},{x=606,y=795},{x=593,y=1249},{x=61,y=1239},{x=67,y=738},{x=170,y=480},{x=294,y=362},{x=482,y=260},{x=592,y=221},{x=1317,y=39}]'

put 'health:hospital_floor_room', 'aaa_0_1', 'f1:id', '1'
put 'health:hospital_floor_room', 'aaa_0_1', 'f1:name', 'A'
put 'health:hospital_floor_room', 'aaa_0_1', 'f1:floorId', '0'
put 'health:hospital_floor_room', 'aaa_0_1', 'f1:hospitalId', 'aaa'
put 'health:hospital_floor_room', 'aaa_0_1', 'f1:corners', '[{x=1305,y=71},{x=1349,y=245},{x=1115,y=305},{x=1070,y=113},{x=1305,y=71}]'
put 'health:hospital_floor_room', 'aaa_0_1', 'f1:center', '{x=1207,y=177}'

put 'health:hospital_floor_room', 'aaa_0_2', 'f1:id', '2'
put 'health:hospital_floor_room', 'aaa_0_2', 'f1:name', 'B'
put 'health:hospital_floor_room', 'aaa_0_2', 'f1:floorId', '0'
put 'health:hospital_floor_room', 'aaa_0_2', 'f1:hospitalId', 'aaa'
put 'health:hospital_floor_room', 'aaa_0_2', 'f1:corners', '[{x=1029,y=133},{x=1076,y=309},{x=962,y=340},{x=913,y=163},{x=1029,y=133}]'
put 'health:hospital_floor_room', 'aaa_0_2', 'f1:center', '{x=994,y=227}'

put 'health:hospital_floor_room', 'aaa_0_3', 'f1:id', '3'
put 'health:hospital_floor_room', 'aaa_0_3', 'f1:name', 'C'
put 'health:hospital_floor_room', 'aaa_0_3', 'f1:floorId', '0'
put 'health:hospital_floor_room', 'aaa_0_3', 'f1:hospitalId', 'aaa'
put 'health:hospital_floor_room', 'aaa_0_3', 'f1:corners', '[{x=895,y=168},{x=942,y=346},{x=828,y=376},{x=779,y=200},{x=895,y=168}]'
put 'health:hospital_floor_room', 'aaa_0_3', 'f1:center', '{x=860,y=270}'

put 'health:hospital_floor_room', 'aaa_0_4', 'f1:id', '4'
put 'health:hospital_floor_room', 'aaa_0_4', 'f1:name', 'D'
put 'health:hospital_floor_room', 'aaa_0_4', 'f1:floorId', '0'
put 'health:hospital_floor_room', 'aaa_0_4', 'f1:hospitalId', 'aaa'
put 'health:hospital_floor_room', 'aaa_0_4', 'f1:corners', '[{x=756,y=205},{x=798,y=376},{x=687,y=408},{x=641,y=237},{x=756,y=205}]'
put 'health:hospital_floor_room', 'aaa_0_4', 'f1:center', '{x=720,y=299}'

put 'health:hospital_floor_room', 'aaa_0_5', 'f1:id', '5'
put 'health:hospital_floor_room', 'aaa_0_5', 'f1:name', 'E'
put 'health:hospital_floor_room', 'aaa_0_5', 'f1:floorId', '0'
put 'health:hospital_floor_room', 'aaa_0_5', 'f1:hospitalId', 'aaa'
put 'health:hospital_floor_room', 'aaa_0_5', 'f1:corners', '[{x=598,y=245},{x=668,y=413},{x=556,y=457},{x=485,y=288},{x=598,y=245}]'
put 'health:hospital_floor_room', 'aaa_0_5', 'f1:center', '{x=579,y=354}'









