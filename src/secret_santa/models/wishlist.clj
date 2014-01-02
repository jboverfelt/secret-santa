(ns secret-santa.models.wishlist
  (:require [monger.collection :as mc]
            [monger.joda-time]
            [secret-santa.models.docutils :as dutils]
            [secret-santa.models.timeutils :as tutils]
            [secret-santa.models.dbutils :refer [setup-db]]
            [secret-santa.models.user :as user])
  (:import [org.bson.types ObjectId]))



(defn get-wishlist-by-id [id]
  (mc/find-map-by-id "wishlists" id))

(defn get-wishlist-by-user [email]
  (let [user-id (:_id (user/find-user-by-email email))]
    (setup-db)
    (mc/find-one-as-map "wishlists" {:user_id user-id})))

(defn get-child-wishlist-by-user [email]
  (let [child-id (:child_id (user/find-user-by-email email))]
    (setup-db)
    (mc/find-one-as-map "wishlists" {:user_id child-id})))

(defn new-wishlist [wishlist]
  (let [oid (ObjectId.)]
    (setup-db)
    (mc/insert-and-return "wishlists" (dutils/setup-new-document wishlist oid))))

(defn update-wishlist [wishlist]
  (let [id (ObjectId. (:_id wishlist))]
    (setup-db)
    (mc/update-by-id "wishlists" id (tutils/touch wishlist))))

(defn delete-wishlist [id]
  (let [oid (ObjectId. id)]
    (mc/remove-by-id "wishlists" oid)))

(comment {:_id (ObjectId.) :items [{:id "uuid" :text "test" :url "amazon"} {:id "uuid" :text "blah" :url "overstock"}]
 :user "2038flhalkbhklakjla"})
