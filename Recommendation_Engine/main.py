import time

import pandas as pd
from sqlalchemy import create_engine

from RecommendationEngine import RecommendationEngine

DATABASE_DRIVER = 'mysql+pymysql'
DATABASE_USER = 'root'
DATABASE_URL = '127.0.0.1'
DATABASE_NAME = 'restaurant'

ratings_fetch_query = 'SELECT users.id AS user_id, items.id AS item_id, ' \
                      'IFNULL((SELECT rating FROM ratings WHERE user_id = users.id AND items.id = ' \
                      'item_id), ' \
                      '0) AS rating ' \
                      'FROM users, items;'

delete_recommendations_query = 'DELETE FROM recommendations'


def connect_to_database(driver=DATABASE_DRIVER, user=DATABASE_USER, url=DATABASE_URL, database=DATABASE_NAME):
    sql_engine = create_engine(f'{driver}://{user}:@{url}/{database}',
                               pool_recycle=3600)
    return sql_engine.connect()


db_connection = connect_to_database()

recommendation_engine = RecommendationEngine(None)

while True:
    ratings_frame = pd.read_sql(ratings_fetch_query, db_connection)
    recommendation_engine.set_frame(ratings_frame)
    users = ratings_frame['user_id'].unique()
    if users.size > 1:
        db_connection.execute(delete_recommendations_query)
        for user_id in users:
            recommendations = recommendation_engine.recommend_for_user(user_id)
            if recommendations.size > 0:
                print(f'[INFO] Processing user {user_id}:')

            for item_id in recommendations:
                print(f'    [INFO] Recommending item {item_id}:')
                db_connection.execute(f'INSERT INTO recommendations(user_id, item_id) VALUES({user_id}, {item_id})')
    time.sleep(3600)
