(ns secret-santa.models.user
  (:require [monger.collection :as mc]
            [monger.joda-time]
            [noir.util.crypt :as crypt]
            [secret-santa.helpers.dbutils :as db]
            [secret-santa.helpers.docutils :as doc])
  (:import [org.bson.types ObjectId]))


(defn find-user-by-id [id]
  (db/setup-db)
  (mc/find-map-by-id "users" id))

(defn find-user-by-email [email]
  (db/setup-db)
  (mc/find-one-as-map "users" {:email email}))

(defn all-users []
  (db/setup-db)
  (mc/find-maps "users"))

(defn create-user [email password full-name]
  (let [oid (ObjectId.)
        digest (crypt/encrypt password)]
    (db/setup-db)
    (mc/insert-and-return "users" (doc/setup-new-document {:email email :digest digest :name full-name} oid))))

(defn update-user [user]
  (db/setup-db)
  (mc/update-by-id "users" (:_id user) (doc/touch user)))

(defn add-child-relationship [[santa child]]
  (db/setup-db)
  (-> (find-user-by-id santa)
      (assoc :child_id child)
      update-user))

(defn update-children [id-map]
  (db/setup-db)
  (doall (map add-child-relationship (seq id-map))))

(defn admin? [user-id]
  (db/setup-db)
  (:admin (find-user-by-id user-id)))
