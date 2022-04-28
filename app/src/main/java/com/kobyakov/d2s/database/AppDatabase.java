package com.kobyakov.d2s.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kobyakov.d2s.dao.FavoritePlayerDao;
import com.kobyakov.d2s.dao.FoundPlayerDao;
import com.kobyakov.d2s.dao.HeroDao;
import com.kobyakov.d2s.dao.ItemDao;
import com.kobyakov.d2s.dao.LeaderBoardDivisionDao;
import com.kobyakov.d2s.dao.LeaderboardDao;
import com.kobyakov.d2s.dao.LobbyTypeDao;
import com.kobyakov.d2s.dao.MatchFullInfoDao;
import com.kobyakov.d2s.dao.ProPlayerDao;
import com.kobyakov.d2s.dao.RecordDao;
import com.kobyakov.d2s.dao.TeamDao;
import com.kobyakov.d2s.dao.UpdateInfoDBDao;
import com.kobyakov.d2s.database.converter.MatchDetailInfoTypeConverter;
import com.kobyakov.d2s.model.FavoritePlayer;
import com.kobyakov.d2s.model.FoundPlayer;
import com.kobyakov.d2s.model.Hero;
import com.kobyakov.d2s.model.Item;
import com.kobyakov.d2s.model.Leaderboard;
import com.kobyakov.d2s.model.LobbyType;
import com.kobyakov.d2s.model.MatchFullInfo;
import com.kobyakov.d2s.model.ProPlayer;
import com.kobyakov.d2s.model.Record;
import com.kobyakov.d2s.model.Team;
import com.kobyakov.d2s.model.TimeRefreshLeaderBoard;
import com.kobyakov.d2s.model.UpdateInfoDB;

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
        Record.class,
        MatchFullInfo.class
}, version = 1)
@TypeConverters(MatchDetailInfoTypeConverter.class)

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
        public abstract MatchFullInfoDao matchFullInfoDao();
}