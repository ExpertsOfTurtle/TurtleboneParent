ALTER TABLE `turtle`.`task` 
CHANGE COLUMN `percentage` `progress` INT(11) NULL DEFAULT '0' ,
ADD COLUMN `total` INT(11) NULL DEFAULT 100 AFTER `difficulty`;

ALTER TABLE `turtle`.`task_user` 
CHANGE COLUMN `percentage` `progress` INT(11) NULL ;

