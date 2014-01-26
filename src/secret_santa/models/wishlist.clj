(ns secret-santa.models.wishlist
  (:require [monger.collection :as mc]
            [monger.joda-time]
            [secret-santa.helpers.docutils :as doc]
            [secret-santa.helpers.dbutils :as db]
            [secret-santa.models.user :as user])
  (:import [org.bson.types ObjectId]))


(defn get-wishlist-by-id [id]
  (do
    (db/setup-db)
    (mc/find-map-by-id "wishlists" id)))

(defn get-wishlist-by-user [user-id]
  (do
    (db/setup-db)
    (mc/find-one-as-map "wishlists" {:user_id user-id})))

(defn get-child-wishlist-by-user [user-id]
  (let [child-id (:child_id (user/find-user-by-id user-id))]
    (db/setup-db)
    (mc/find-one-as-map "wishlists" {:user_id child-id})))

(defn edit-wishlist [wishlist]
  (do
    (db/setup-db)
    (if-let [id (:_id wishlist)]
      (mc/update-by-id "wishlists" id (doc/touch wishlist))
      (mc/insert-and-return "wishlists" (doc/setup-new-document wishlist (ObjectId.))))))

(defn delete-wishlist [id]
  (let [oid (ObjectId. id)]
    (db/setup-db)
    (mc/remove-by-id "wishlists" oid)))
