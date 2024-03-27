import configparser


def read_config(filename="config.ini"):
    config = configparser.ConfigParser()
    config.read(filename)
    return config


def get_database_config(config):
    database_config = config['DATABASE']
    return {
        'HOST': database_config['HOST'],
        'PORT': int(database_config['PORT']),
        'DATABASE_NAME': database_config['DATABASE_NAME'],
        'USERNAME': database_config['USERNAME'],
        'PASSWORD': database_config['PASSWORD']
    }


def get_mongodb_config(config):
    mongodb_config = config['MONGODB']
    return {
        'HOST': mongodb_config['HOST'],
        'PORT': int(mongodb_config['PORT']),
        'DATABASE_NAME': mongodb_config['DATABASE_NAME'],
        'USERNAME': mongodb_config['USERNAME'],
        'PASSWORD': mongodb_config['PASSWORD']
    }
