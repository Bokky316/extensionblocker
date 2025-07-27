// components/common/Pill.tsx

interface PillProps {
  label: string
  onRemove?: () => void
}

export const Pill = ({ label, onRemove }: PillProps) => {
  return (
    <div
      style={{
        display: 'flex',
        alignItems: 'center',
        padding: '4px 10px',
        border: '1px solid #D1D5DB',
        borderRadius: '8px',
        fontSize: '11px',
        color: '#1F2937',
        marginRight: '12px',
        marginBottom: '8px',
        backgroundColor: 'transparent',
        whiteSpace: 'nowrap',
      }}
    >
      <span style={{ marginRight: '8px' }}>{label}</span>
      {onRemove && (
        <button
          onClick={onRemove}
          style={{
            background: 'none',
            border: 'none',
            padding: 0,
            margin: 0,
            fontSize: '12px',
            color: '#9CA3AF',
            lineHeight: 1,
            cursor: 'pointer',
          }}
          onMouseOver={(e) => (e.currentTarget.style.color = '#EF4444')}
          onMouseOut={(e) => (e.currentTarget.style.color = '#9CA3AF')}
        >
          ×
        </button>
      )}
    </div>
  )
}
