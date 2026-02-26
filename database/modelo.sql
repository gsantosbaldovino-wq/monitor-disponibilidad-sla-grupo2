```sql
CREATE TABLE services (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(500) NOT NULL,
    type VARCHAR(20) NOT NULL, -- 'http', 'https', 'ping', 'port'
    method VARCHAR(10), -- 'GET', 'POST', 'HEAD' para HTTP
    expected_status INTEGER, -- 200 para HTTP
    expected_response TEXT, -- Texto esperado en respuesta
    port INTEGER, -- Para monitoreo de puertos
    check_interval INTEGER DEFAULT 60, -- segundos
    timeout INTEGER DEFAULT 30, -- segundos
    retries INTEGER DEFAULT 2,
    is_active BOOLEAN DEFAULT true,
    notifications_enabled BOOLEAN DEFAULT true,
    sla_target DECIMAL(5,2) DEFAULT 99.9, -- % disponibilidad objetivo
    sla_response_time INTEGER, -- ms objetivo
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Tabla de Checks/Verificaciones**
```sql
CREATE TABLE checks (
    id BIGSERIAL PRIMARY KEY,
    service_id INTEGER REFERENCES services(id) ON DELETE CASCADE,
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response_time INTEGER, -- ms
    status_code INTEGER, -- HTTP status o código de error
    status VARCHAR(20) NOT NULL, -- 'up', 'down', 'degraded'
    error_message TEXT,
    response_size INTEGER, -- bytes
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

Tabla de Historial de Estado**
```sql
CREATE TABLE service_status_history (
    id BIGSERIAL PRIMARY KEY,
    service_id INTEGER REFERENCES services(id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL, -- 'up', 'down', 'maintenance'
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    duration INTEGER, -- segundos (calculado cuando ended_at no es null)
    check_id_start BIGINT REFERENCES checks(id), -- check que inició el estado
    check_id_end BIGINT REFERENCES checks(id), -- check que finalizó el estado
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
Tabla de Métricas Agregadas (para rendimiento)**
```sql
CREATE TABLE metrics_hourly (
    id BIGSERIAL PRIMARY KEY,
    service_id INTEGER REFERENCES services(id) ON DELETE CASCADE,
    date_hour TIMESTAMP NOT NULL, -- AAAA-MM-DD HH:00:00
    total_checks INTEGER DEFAULT 0,
    up_checks INTEGER DEFAULT 0,
    down_checks INTEGER DEFAULT 0,
    avg_response_time INTEGER,
    min_response_time INTEGER,
    max_response_time INTEGER,
    uptime_percentage DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(service_id, date_hour)
);

CREATE TABLE metrics_daily (
    id BIGSERIAL PRIMARY KEY,
    service_id INTEGER REFERENCES services(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    uptime_percentage DECIMAL(5,2),
    avg_response_time INTEGER,
    total_downtime INTEGER, -- segundos
    total_outages INTEGER,
    sla_compliant BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(service_id, date)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(100),
    team_id INTEGER REFERENCES teams(id),
    role VARCHAR(50) DEFAULT 'member',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
