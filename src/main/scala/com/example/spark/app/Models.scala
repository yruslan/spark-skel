package com.example.spark.app

object Models {

  case class FootballTeam(
                           name: String,
                           league: String,
                           matches_played: Option[Int] = None,
                           goals_this_season: Option[Int] = None,
                           top_goal_scorer: Option[String] = None,
                           wins: Option[Int] = None
                         )
}
