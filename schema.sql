CREATE SCHEMA `sports_schedule` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `sports_schedule`.`league` (
  `id` VARCHAR(36) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `sports_schedule`.`venue` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `country` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `sports_schedule`.`team` (
  `id` INT(11) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `abbreviation` VARCHAR(255) NOT NULL,
  `league_id` VARCHAR(36) NOT NULL,
  `venue_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `team_leaguefk_idx` (`league_id` ASC),
  CONSTRAINT `team_leaguefk`
    FOREIGN KEY (`league_id`)
    REFERENCES `sports_schedule`.`league` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  INDEX `team_venuefk_idx` (`venue_id` ASC),
  CONSTRAINT `team_venuefk`
    FOREIGN KEY (`venue_id`)
    REFERENCES `sports_schedule`.`venue` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `sports_schedule`.`game` (
  `id` INT(11) NOT NULL,
  `week` INT(11) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `away_team_id` INT(11) NOT NULL,
  `home_team_id` INT(11) NOT NULL,
  `venue_id` INT(11) NOT NULL,
  `venue_allegiance` VARCHAR(100) NOT NULL,
  `schedule_status` VARCHAR(100) NOT NULL,
  `original_start_time` DATETIME NULL,
  `delayed_or_postponed_reason` VARCHAR(255) NULL,
  `played_status` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `game_awayteamfk_idx` (`away_team_id` ASC),
  INDEX `game_hometeamfk_idx` (`home_team_id` ASC),
  INDEX `game_venuefk_idx` (`venue_id` ASC),
  CONSTRAINT `game_awayteamfk`
    FOREIGN KEY (`away_team_id`)
    REFERENCES `sports_schedule`.`team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `game_hometeamfk`
    FOREIGN KEY (`home_team_id`)
    REFERENCES `sports_schedule`.`team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `game_venuefk`
    FOREIGN KEY (`venue_id`)
    REFERENCES `sports_schedule`.`venue` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

