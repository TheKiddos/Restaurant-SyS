import pandas as pd
from scipy.sparse import csr_matrix
from sklearn.neighbors import NearestNeighbors


class RecommendationEngine:
    """
    This class is used to give recommendations using the Collaborative filtering user-based algorithm
    """
    def __init__(self, ratings_frame, similarity_strategy='knn'):
        """
        :arg ratings_frame: A pandas dataframe that has three columns user_id, item_id and rating.
        :arg similarity_strategy: The algorithm used to determine the similarity between two users currently only the K-Nearest Neighbours algorithm is supported (and to be honest I have no plans of supporting any other algorithms)
        """
        self.ratings_frame = ratings_frame
        self.similarity_strategy = similarity_strategy
        self.user_item_frame = self.construct_user_item_frame()
        self.engine = NearestNeighbors(metric='cosine', algorithm='brute') if similarity_strategy == 'knn' else None
        self.learn_similarities()

    def set_frame(self, ratings_frame):
        """
        This method is useful in case the dataframe was updated outside.
        :arg ratings_frame: the new ratings frame.
        """
        self.ratings_frame = ratings_frame
        if ratings_frame is None or ratings_frame.empty:
            return
        self.user_item_frame = self.construct_user_item_frame()
        self.learn_similarities()

    def construct_user_item_frame(self):
        """
        Creates the user-item table where each cell(r, c) represents the rating given by the user at row r for the item at column c
        :return: The user-item table as a pandas dataframe
        """
        if self.ratings_frame is None:
            return None
        return pd.pivot_table(self.ratings_frame, index=['user_id'], columns='item_id', values="rating")

    def learn_similarities(self):
        """
        Learn the similarities of the users
        """
        if self.ratings_frame is None:
            return
        self.engine.fit(csr_matrix(self.user_item_frame.values))

    def recommend_for_user(self, user_id):
        """
        Return a numpy array of recommended items (ids) for the specified user
        :param user_id: The user_id to recommend items to
        :return: numpy array of item ids
        """
        if self.ratings_frame is None:
            return None
        indices = self.engine.kneighbors(self.user_item_frame.iloc[user_id - 1].values.reshape(1, -1), n_neighbors=2, return_distance=False)
        indices = indices + 1  # ids starts at 1 while indices starts at 0
        # index 0 is the user himself so we choose 1
        nearest_user = self.user_item_frame.loc[indices.flatten()[1]]
        user = self.user_item_frame.loc[user_id]
        recommendations = nearest_user[user == 0]
        recommendations = recommendations[nearest_user > 3]
        return recommendations.index.to_numpy()
