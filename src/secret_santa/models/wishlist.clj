(ns secret-santa.models.wishlist
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.joda-time]
            [secret-santa.models.docutils :as dutils]
            [secret-santa.models.timeutils :as tutils])
  (:import [org.bson.types ObjectId]))


(defn setup-db []
  (let [uri (get (System/getenv) "MONGOHQ_URI" "mongodb://127.0.0.1/santa")]
    (when )
    (mg/connect-via-uri! uri)
    (mg/set-db! (mg/get-db "santa"))))

(defn get-wishlist-by-id [id]
  (mc/find-map-by-id "wishlists" id))

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
