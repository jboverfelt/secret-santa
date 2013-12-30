(ns secret-santa.models.timeutils
  (:require [clj-time.core :as ctime]))

(defn add-created-and-updated [document]
  (assoc document :created_at (ctime/now) :updated_at (ctime/now)))

(defn touch [document]
  (assoc document :updated_at (ctime/now)))
