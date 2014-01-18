(ns secret-santa.models.user
  (:require [monger.collection :as mc]
            [monger.joda-time]
            [noir.util.crypt :as crypt]
            [secret-santa.helpers.dbutils :as db]
            [secret-santa.helpers.docutils :as doc])
  (:import [org.bson.types ObjectId]))


(defn find-user-by-id [id]
  (do
    (db/setup-db)
    (mc/find-map-by-id "users" id)))

(defn find-user-by-email [email]
  (do
    (db/setup-db)
    (mc/find-one-as-map "users" {:email email})))

(defn create-user [email password]
  (let [oid (ObjectId.)
        digest (crypt/encrypt password)]
    (db/setup-db)
    (mc/insert-and-return "users" (doc/setup-new-document {:email email :digest digest} oid))))
