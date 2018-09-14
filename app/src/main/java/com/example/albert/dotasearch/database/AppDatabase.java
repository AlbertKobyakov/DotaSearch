package com.example.albert.dotasearch.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.albert.dotasearch.dao.FavoritePlayerDao;
import com.example.albert.dotasearch.dao.FoundPlayerDao;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.dao.ItemDao;
import com.example.albert.dotasearch.dao.LeaderBoardDivisionDao;
import com.example.albert.dotasearch.dao.LeaderboardDao;
import com.example.albert.dotasearch.dao.LobbyTypeDao;
import com.example.albert.dotasearch.dao.MatchShortInfoDao;
import com.example.albert.dotasearch.dao.ProPlayerDao;
import com.example.albert.dotasearch.dao.RecordDao;
import com.example.albert.dotasearch.dao.TeamDao;
import com.example.albert.dotasearch.dao.UpdateInfoDBDao;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.Leaderboard;
import com.example.albert.dotasearch.model.LobbyType;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.model.Record;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.model.TimeRefreshLeaderBoard;
import com.example.albert.dotasearch.model.UpdateInfoDB;

@Database(entities = {
        Hero.class,
        ProPlayer.class,
        Leaderboard.class,
        Item.class,
        FoundPlayer.class,
        LobbyType.class,
        FavoritePlayer.class,
        TimeRefreshLeaderBoard.class,
        Team.class,
        UpdateInfoDB.class,
        Record.class
}, version = 1)

    public abstract class AppDatabase extends RoomDatabase {
        public abstract HeroDao heroDao();
        public abstract ProPlayerDao proPlayerDao();
        public abstract LeaderboardDao leaderboardDao();
        public abstract ItemDao itemDao();
        public abstract FoundPlayerDao foundPlayerDao();
        public abstract LobbyTypeDao lobbyTypeDao();
        public abstract FavoritePlayerDao favoritePlayerDao();
        public abstract LeaderBoardDivisionDao leaderBoardDivisionDao();
        public abstract TeamDao teamDao();
        public abstract UpdateInfoDBDao updateInfoDBDao();
        public abstract RecordDao recordDao();
}
