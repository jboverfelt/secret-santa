(ns secret-santa.models.user
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.joda-time]
            [secret-santa.models.docutils :as dutils]
            [secret-santa.models.timeutils :as tutils]
            [secret-santa.models.user :as user])
  (:import [org.bson.types ObjectId]))


(defn find-user-by-email [email]
  email)
