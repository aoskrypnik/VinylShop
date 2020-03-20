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
      en: 'Composer',
      ua: 'Композитор',
      properties: {
        composerName: {
          ua: "Ім'я",
          en: "Name"
        },
        composerCountryCode: {
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
        },
        artistIds: {
          ua: 'Виконавці',
          en: 'Artists'
        },
        bandIds: {
          ua: 'Групи',
          en: 'Bands'
        },
        featuringArtistIds: {
          ua: 'Другорядні виконавці',
          en: 'Featuring artists'
        },
        featuringBandIds: {
          ua: 'Другорядні групи',
          en: 'Featuring bands'
        },
        artistBindings: {
          ua: 'Зв\'язки з виконавцями',
          en: 'Artists bindings'
        },
        albumIds: {
          ua: 'Альбоми',
          en: 'Albums'
        }
      }
    },
    artist: {
      en: 'Artist',
      ua: 'Виконавець',
      properties: {
        artistAlias: {
          ua: 'Псевдонім',
          en: 'Alias'
        },
        isArtistActive: {
          ua: 'Активний',
          en: 'Active'
        },
        countryCode: {
          ua: 'Країна',
          en: 'Country'
        },
        artistName: {
          ua: 'Iм\'я',
          en: 'Name'
        },
        artistBirthDate: {
          ua: 'Дата народження',
          en: 'Birth date'
        },
        artistDeathDate: {
          ua: 'Дата смерті',
          en: 'Death date'
        },
        currentBandAliases: {
          ua: 'Поточні групи',
          en: 'Current bands'
        },
        previousBandAliases: {
          ua: 'Колишні групи',
          en: 'Former bands'
        },
        trackCatalogNums: {
          ua: 'Треки',
          en: 'Tracks'
        },
        featuringTrackCatalogNums: {
          ua: 'Другорядні треки',
          en: 'Featuring tracks'
        },
        artistBindings: {
          ua: 'Зв\'язки з треками',
          en: 'Track bindings'
        },
        participations: {
          ua: 'Зв\'язки з групами',
          en: 'Bands bindings'
        }
      }
    },
    artist2track: {
      en: 'Artist - track connection',
      ua: 'Зв\'язок трек - виконавець',
      properties: {
        trackCatalogNum: {
          en: 'Track',
          ua: 'Трек'
        },
        artistAlias: {
          en: 'Artist',
          ua: 'Виконавець'
        },
        isFeaturing: {
          en: 'Featuring',
          ua: 'Другорядний'
        }
      }
    },
    band: {
      en: 'Band',
      ua: 'Група',
      properties: {
        bandAlias: {
          ua: 'Назва',
          en: 'Alias'
        },
        isBandActive: {
          ua: 'Активна',
          en: 'Active'
        },
        countryCode: {
          ua: 'Країна',
          en: 'Country'
        },
        startYear: {
          ua: 'Дата заснування',
          en: 'Start date'
        },
        endYear: {
          ua: 'Дата розпаду',
          en: 'End date'
        },
        currentArtistAliases: {
          ua: 'Поточні виконавці',
          en: 'Current artists'
        },
        previousArtistAliases: {
          ua: 'Колишні виконавці',
          en: 'Previous artists'
        },
        trackCatalogNums: {
          ua: 'Треки',
          en: 'Tracks'
        },
        featuringTracksCatalogNums: {
          ua: 'Другорядні треки',
          en: 'Featuring tracks'
        },
        participations: {
          ua: 'Зв\'язки з учасниками',
          en: 'Artists bindings'
        }
      }
    },
    participation: {
      en: 'Band participation',
      ua: 'Участь у групі',
      properties: {
        artistAlias: {
          en: 'Artist',
          ua: 'Виконавець'
        },
        bandAlias: {
          en: 'Band',
          ua: 'Група'
        },
        joinDate: {
          en: 'Join date',
          ua: 'Дата вступу'
        },
        exitDate: {
          en: 'Exit date',
          ua: 'Дата виходу'
        }
      }
    },
    album: {
      en: 'Album',
      ua: 'Альбом',
      properties: {
        albumCatalogNum: {
          en: 'Catalog #',
          ua: 'Номер каталогу'
        },
        releaseYear: {
          en: 'Release year',
          ua: 'Рік випуску'
        },
        albumGenres: {
          en: 'Genres',
          ua: 'Жанри'
        },
        albumName: {
          en: 'Name',
          ua: 'Назва'
        },
        variousArtists: {
          en: 'Compilation',
          ua: 'Компіляція'
        },
        trackCatalogNums: {
          en: 'Tracks',
          ua: 'Треки'
        },
        rock: {
          en: 'Rock',
          ua: 'Рок'
        },
        pop: {
          en: 'Pop',
          ua: 'Поп'
        },
        classic: {
          en: 'Classical',
          ua: 'Класична'
        },
        indie: {
          en: 'Indie',
          ua: 'Інді'
        },
        electronic: {
          en: 'Electronic',
          ua: 'Електроніка'
        }
      }
    },
    release: {
      en: 'Release',
      ua: 'Реліз',
      properties: {
        releaseBarcode: {
          en: 'Barcode #',
          ua: 'Штрих-код'
        },
        albumCatalogNum: {
          en: 'Album',
          ua: 'Альбом'
        },
        releaseCountryCode: {
          en: 'Country',
          ua: 'Країна'
        },
        releaseDate: {
          en: 'Release date',
          ua: 'Дата випуску'
        },
        recordSize: {
          en: 'Record size',
          ua: 'Розмір платівки'
        },
        recordSpeed: {
          en: 'Record speed',
          ua: 'Швидкість програвання'
        },
        copiesCount: {
          en: 'Copies count',
          ua: 'Наклад'
        },
        isRepress: {
          en: 'Repress',
          ua: 'Перевидання'
        },
        label: {
          en: 'Label',
          ua: 'Лейбл'
        }
      }
    }
  }
}