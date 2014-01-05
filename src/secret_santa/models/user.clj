(ns secret-santa.models.user
  (:require [monger.collection :as mc]
            [monger.joda-time]
            [noir.util.crypt :as crypt]
            [secret-santa.helpers.dbutils :as db]
            [secret-santa.helpers.docutils :as doc])
  (:import [org.bson.types ObjectId]))


(defn find-user-by-id [id]
  {:email "test@test.com" :_id id})

(defn find-user-by-email [email]
  {:email email :_id "123"})

(defn create-user [email password]
  (let [oid (ObjectId.)
        digest (crypt/encrypt password)]
    (db/setup-db)
    (mc/insert-and-return (doc/setup-new-document {:email email :digest digest} oid))))
