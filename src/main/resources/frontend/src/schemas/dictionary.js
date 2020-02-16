export default {
  schemas: {
    client: {
      en: 'Customer',
      ua: 'Покупець',
      properties: {
        phone: {
          ua: 'Номер телефону'
        },
        name: {
          ua: 'Ім\'я'
        },
        email: {
          ua: 'Електронна пошта'
        },
        type: {
          ua: 'Статус'
        },
        "1": {
          ua: 'Постійний'
        },
        "2": {
          ua: 'Непостійний'
        }
      }
    },
    check: {
      en: 'Check',
      ua: 'Чек',
      properties: {
        date: {
          ua: 'Дата',
          en: 'Date'
        },
        client: {
          ua: 'Покупець',
          en: 'Customer'
        }
      }
    },
    composer: {
      en:'Composer',
      ua: 'Композитор',
      properties: {
        composerName: {
          ua: "Ім'я",
          en: "Name"
        },
        countryCode: {
          ua: 'Країна',
          en: 'Country'
        },
        activityStart: {
          ua: 'Початок діяльності',
          en: 'Activity start'
        },
        activityEnd: {
          ua: 'Кінець активності',
          en: 'Activity end'
        },
        trackIds: {
          ua: 'Композиції',
          en: 'Tracks'
        }
      }
    },
    track: {
      en: 'Track',
      ua: 'Композиція',
      properties: {
        trackCatalogNum: {
          ua: 'Номер у каталозі',
          en: 'Catalog number'
        },
        trackName: {
          ua: 'Назва',
          en: 'Name'
        },
        duration: {
          ua: 'Тривалість',
          en: 'Duration'
        },
        composerIds: {
          ua: 'Композитори',
          en: 'Composers'
        },
        languages: {
          ua: 'Мови',
          en: 'Languages'
        }
      }
    }
  }
  
}