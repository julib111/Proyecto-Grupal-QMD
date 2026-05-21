-- =========================================================================
-- 1. POBLAR CLIENTES (Cumple con CRUD: ≥ 10 registros)
-- =========================================================================
INSERT INTO clientes (identificacion, nombre, email, telefono, activo) VALUES
('1094000111', 'Ana María Rojas', 'ana.rojas@email.com', '3110001111', true),
('1094000222', 'Carlos Arturo Mejía', 'carlos.mejia@email.com', '3120002222', true),
('1094000333', 'Luisa Fernanda Soto', 'luisa.soto@email.com', '3130003333', true),
('1094000444', 'Jorge Iván Cárdenas', 'jorge.cardenas@email.com', '3140004444', true),
('1094000555', 'Valentina Osorio', 'valentina.osorio@email.com', '3150005555', true),
('1094000666', 'Andrés Felipe Castaño', 'andres.castano@email.com', '3160006666', true),
('1094000777', 'Camila Arias Gómez', 'camila.arias@email.com', '3170007777', true),
('1094000888', 'Santiago Ruiz', 'santiago.ruiz@email.com', '3180008888', true),
('1094000999', 'Diana Patricia Castro', 'diana.castro@email.com', '3190009999', false), -- Cliente inactivo
('1094000000', 'Sebastián Ríos', 'sebastian.rios@email.com', '3000000000', true);

-- =========================================================================
-- 2. POBLAR CABAÑAS (Mezcla de disponibles, ocupadas y en mantenimiento)
-- =========================================================================
INSERT INTO cabanas (nombre, capacidad, precio_noche, estado) VALUES
('Eco-hab Valle del Cocora', 2, 200000.0, 'DISPONIBLE'),
('Cabaña Mirador Filandia', 4, 350000.0, 'OCUPADA'),
('Refugio Salento', 6, 500000.0, 'DISPONIBLE'),
('Finca Cafetera La Castellana', 8, 700000.0, 'MANTENIMIENTO'),
('Cabaña El Ocaso', 2, 250000.0, 'DISPONIBLE'),
('Villa Los Nevados', 10, 900000.0, 'DISPONIBLE');

-- =========================================================================
-- 3. POBLAR CUPONES (Para probar reglas de descuentos)
-- =========================================================================
INSERT INTO cupones (codigo, descuento_porcentaje, estado) VALUES
('BIENVENIDA10', 10.0, 'ACTIVO'),   -- Para flujo feliz
('SEMANASANTA20', 20.0, 'USADO'),    -- Para regla bloqueante (Cupón ya usado)
('ESTUDIANTE15', 15.0, 'ACTIVO'),   -- Alternativa válida
('ERROR50', 50.0, 'USADO');          -- Alternativa inválida

-- =========================================================================
-- 4. POBLAR RESERVAS (Casos de prueba para las reglas de negocio)
-- Nota: La fecha actual asumida es 20 de mayo de 2026.
-- =========================================================================

-- CASO 1: FLUJO FELIZ. Reserva perfecta para hacer check-in HOY.
-- Estado: PENDIENTE, Fecha de inicio: HOY (20-May-2026).
INSERT INTO reservas (fecha_inicio, fecha_fin, saldo_pendiente, estado, cabana_id, cliente_id) VALUES
('2026-05-20', '2026-05-23', 600000.0, 'PENDIENTE', 1, 1);

-- CASO 2: REGLA BLOQUEANTE (Fecha prematura).
-- Intento de check-in antes de tiempo. Fecha de inicio: próxima semana.
INSERT INTO reservas (fecha_inicio, fecha_fin, saldo_pendiente, estado, cabana_id, cliente_id) VALUES
('2026-05-25', '2026-05-28', 500000.0, 'PENDIENTE', 3, 2);

-- CASO 3: REGLA BLOQUEANTE (Estado inválido - Ya realizó check-in).
-- La reserva no está PENDIENTE.
INSERT INTO reservas (fecha_inicio, fecha_fin, saldo_pendiente, estado, cabana_id, cliente_id) VALUES
('2026-05-18', '2026-05-22', 0.0, 'CHECK_IN_REALIZADO', 2, 3);

-- CASO 4: REGLA BLOQUEANTE (Estado inválido - Cancelada).
INSERT INTO reservas (fecha_inicio, fecha_fin, saldo_pendiente, estado, cabana_id, cliente_id) VALUES
('2026-05-19', '2026-05-21', 250000.0, 'CANCELADA', 5, 4);

-- CASO 5: Otro flujo feliz para aplicar cupón diferente o pagar con tarjeta.
INSERT INTO reservas (fecha_inicio, fecha_fin, saldo_pendiente, estado, cabana_id, cliente_id) VALUES
('2026-05-20', '2026-05-22', 1800000.0, 'PENDIENTE', 6, 5);